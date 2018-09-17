package com.nearsoft.fgaribay.mgmt;

import com.nearsoft.fgaribay.mgmt.config.ProductProperties;
import com.nearsoft.fgaribay.mgmt.model.Product;
import com.nearsoft.fgaribay.mgmt.model.ProductRequest;
import com.nearsoft.fgaribay.mgmt.model.ProductResponse;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class BrokerConnector {

    private final RabbitTemplate rabbitTemplate;

    private ProductProperties.Queues queueProperties;

    @Autowired
    public BrokerConnector(RabbitTemplate rabbitTemplate, ProductProperties properties) {
        this.rabbitTemplate = rabbitTemplate;
        this.queueProperties = properties.getQueues();
    }

    public List<Product> retrieveProductList() {
        ProductRequest request = new ProductRequest("list", null);
        ProductResponse response =
                (ProductResponse) rabbitTemplate.convertSendAndReceive(queueProperties.getListName(), request);

        if (response == null) {
            throw new RuntimeException(
                    "Failed to get a response from the broker while attempting to get a product list.");
        } else if (response.isError()) {
            throw new RuntimeException(response.getErrorMessage());
        }

        return response.getProducts();
    }

    public void createProduct(Product product) {
        ProductRequest request = new ProductRequest("create", Collections.singletonList(product));
        ProductResponse response =
                (ProductResponse) rabbitTemplate.convertSendAndReceive(queueProperties.getCreationName(), request);

        if (response == null) {
            throw new RuntimeException(
                    "Failed to get a response from the broker while attempting to create the product.");
        } else if (response.isError()) {
            throw new RuntimeException(response.getErrorMessage());
        }
    }
}
