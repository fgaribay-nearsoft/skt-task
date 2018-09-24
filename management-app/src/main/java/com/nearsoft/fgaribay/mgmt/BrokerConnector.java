package com.nearsoft.fgaribay.mgmt;

import com.nearsoft.fgaribay.mgmt.config.properties.ExchangeProperties;
import com.nearsoft.fgaribay.mgmt.config.properties.RoutingKeyProperties;
import com.nearsoft.fgaribay.mgmt.exceptions.UnresponsiveMicroserviceException;
import com.nearsoft.fgaribay.mgmt.exceptions.ProductDataException;
import com.nearsoft.fgaribay.mgmt.model.Product;
import com.nearsoft.fgaribay.mgmt.model.ServiceRequest;
import com.nearsoft.fgaribay.mgmt.model.ServiceResponse;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BrokerConnector {

    private final RabbitTemplate rabbitTemplate;

    private ExchangeProperties exchangeProperties;
    private RoutingKeyProperties routingKeyProperties;

    public BrokerConnector(RabbitTemplate rabbitTemplate, ExchangeProperties exchangeProperties, RoutingKeyProperties routingKeyProperties) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchangeProperties = exchangeProperties;
        this.routingKeyProperties = routingKeyProperties;
    }

    public List<Product> retrieveProductList() {
        ServiceRequest request = new ServiceRequest<>("list", null);
        ServiceResponse<List<Product>> response =
                (ServiceResponse) rabbitTemplate.convertSendAndReceive(
                        exchangeProperties.getListName(), routingKeyProperties.getListName(), request);

        if (response == null) {
            throw new UnresponsiveMicroserviceException(
                    "Failed to get a response from the broker while attempting to get a product list.");
        } else if (response.isError()) {
            throw new ProductDataException(response.getErrorMessage());
        }

        return response.getData();
    }

    public void createProduct(Product product) {
        ServiceRequest<Product> request = new ServiceRequest<>("create", product);
        ServiceResponse response =
                (ServiceResponse) rabbitTemplate.convertSendAndReceive(
                        exchangeProperties.getCreationName(), routingKeyProperties.getCreationName(), request);

        if (response == null) {
            throw new UnresponsiveMicroserviceException(
                    "Failed to get a response from the broker while attempting to create the product.");
        } else if (response.isError()) {
            throw new ProductDataException(response.getErrorMessage());
        }
    }
}
