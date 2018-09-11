package com.nearsoft.fgaribay.mgmt;

import com.nearsoft.fgaribay.mgmt.model.Product;
import com.nearsoft.fgaribay.mgmt.model.ProductRequest;
import com.nearsoft.fgaribay.mgmt.model.ProductResponse;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class BrokerConnector {
    private final RabbitTemplate rabbitTemplate;
    @Value("product.create.queue.name")
    private String productCreationQueueName;
    @Value("product.list.queue.name")
    private String productListQueueName;

    @Autowired
    public BrokerConnector(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public List<Product> retrieveProductList() {
        ProductRequest request = new ProductRequest("list", null);
        ProductResponse response =
                (ProductResponse) rabbitTemplate.convertSendAndReceive(productListQueueName, request);

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
                (ProductResponse) rabbitTemplate.convertSendAndReceive(productCreationQueueName, request);

        if (response == null) {
            throw new RuntimeException(
                    "Failed to get a response from the broker while attempting to create the product.");
        } else if (response.isError()) {
            throw new RuntimeException(response.getErrorMessage());
        }
    }
}
