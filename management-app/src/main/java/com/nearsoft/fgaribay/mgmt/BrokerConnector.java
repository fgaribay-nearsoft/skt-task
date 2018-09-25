package com.nearsoft.fgaribay.mgmt;

import com.nearsoft.fgaribay.mgmt.config.properties.ExchangeProperties;
import com.nearsoft.fgaribay.mgmt.config.properties.RoutingKeyProperties;
import com.nearsoft.fgaribay.mgmt.exceptions.ProductDataException;
import com.nearsoft.fgaribay.mgmt.exceptions.UnresponsiveBrokerException;
import com.nearsoft.fgaribay.mgmt.exceptions.UnresponsiveMicroserviceException;
import com.nearsoft.fgaribay.mgmt.model.Product;
import com.nearsoft.fgaribay.mgmt.model.ServiceRequest;
import com.nearsoft.fgaribay.mgmt.model.ServiceResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpConnectException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BrokerConnector {

  private static final Logger LOGGER = LoggerFactory.getLogger(BrokerConnector.class);

  private final RabbitTemplate rabbitTemplate;

  private ExchangeProperties exchangeProperties;
  private RoutingKeyProperties routingKeyProperties;

  public BrokerConnector(
      RabbitTemplate rabbitTemplate,
      ExchangeProperties exchangeProperties,
      RoutingKeyProperties routingKeyProperties) {
    this.rabbitTemplate = rabbitTemplate;
    this.exchangeProperties = exchangeProperties;
    this.routingKeyProperties = routingKeyProperties;
  }

  public List<Product> retrieveProductList()
      throws UnresponsiveMicroserviceException, ProductDataException, UnresponsiveBrokerException {
    LOGGER.debug("Sending request to AMQP broker for a product list.");

    ServiceRequest request = new ServiceRequest<>("list", null);
    ServiceResponse<List<Product>> response = null;

    try {
      response =
          (ServiceResponse<List<Product>>)
              rabbitTemplate.convertSendAndReceive(
                  exchangeProperties.getListName(), routingKeyProperties.getListName(), request);
    } catch (AmqpConnectException e) {
      LOGGER.error("Failed to obtain a connection to the AMQP broker.", e);
      throw new UnresponsiveBrokerException("Failed to connect with the AMQP broker.");
    }

    if (response == null) {
      LOGGER.error(
          "Failed to obtain a response from the microservice even though a connection to the broker was achieved.");
      throw new UnresponsiveMicroserviceException(
          "Failed to get a response from the broker while attempting to get a product list.");
    } else if (response.isError()) {
      LOGGER.error(
          "Failed to retrieve a product list because of a database error: {}",
          response.getErrorMessage());
      throw new ProductDataException(response.getErrorMessage());
    }

    return response.getData();
  }

  public void createProduct(Product product)
      throws UnresponsiveBrokerException, UnresponsiveMicroserviceException, ProductDataException {
    LOGGER.debug("Sending request to AMQP broker to create a product: ", product);

    ServiceRequest<Product> request = new ServiceRequest<>("create", product);
    ServiceResponse<Product> response = null;

    try {
      response =
          (ServiceResponse<Product>)
              rabbitTemplate.convertSendAndReceive(
                  exchangeProperties.getCreationName(),
                  routingKeyProperties.getCreationName(),
                  request);
    } catch (AmqpConnectException e) {
      LOGGER.error(
          "Failed to obtain a response from the microservice even though a connection to the broker was achieved.");
      throw new UnresponsiveBrokerException("Failed to connect with the AMQP broker.");
    }

    if (response == null) {
      throw new UnresponsiveMicroserviceException(
          "Failed to get a response from the broker while attempting to create the product.");
    } else if (response.isError()) {
      LOGGER.error(
          "Failed to create a product because of a database error: {}", response.getErrorMessage());
      throw new ProductDataException(response.getErrorMessage());
    }
  }
}
