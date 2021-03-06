package com.nearsoft.fgaribay.mgmt;

import com.nearsoft.fgaribay.mgmt.config.properties.ExchangeProperties;
import com.nearsoft.fgaribay.mgmt.config.properties.RoutingKeyProperties;
import com.nearsoft.fgaribay.mgmt.exceptions.UnresponsiveMicroserviceException;
import com.nearsoft.fgaribay.mgmt.exceptions.ProductDataException;
import com.nearsoft.fgaribay.mgmt.model.Product;
import com.nearsoft.fgaribay.mgmt.model.ProductRequest;
import com.nearsoft.fgaribay.mgmt.model.ProductResponse;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
        ProductRequest request = new ProductRequest("list", null);
        ProductResponse response =
                (ProductResponse) rabbitTemplate.convertSendAndReceive(
                        exchangeProperties.getListName(), routingKeyProperties.getListName(), request);

        if (response == null) {
            throw new UnresponsiveMicroserviceException(
                    "Failed to get a response from the broker while attempting to get a product list.");
        } else if (response.isError()) {
            throw new ProductDataException(response.getErrorMessage());
        }

        return response.getProducts();
    }

    public void createProduct(Product product) {
        List<Product> products = new ArrayList<>();
        products.add(product);
        ProductRequest request = new ProductRequest("create", products);
        ProductResponse response =
                (ProductResponse) rabbitTemplate.convertSendAndReceive(
                        exchangeProperties.getCreationName(), routingKeyProperties.getCreationName(), request);

        if (response == null) {
            throw new UnresponsiveMicroserviceException(
                    "Failed to get a response from the broker while attempting to create the product.");
        } else if (response.isError()) {
            throw new ProductDataException(response.getErrorMessage());
        }
    }
}
