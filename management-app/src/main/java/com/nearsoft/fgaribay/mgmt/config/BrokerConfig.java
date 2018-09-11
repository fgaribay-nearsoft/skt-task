package com.nearsoft.fgaribay.mgmt.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BrokerConfig {
    @Value("product.create.queue.name")
    private String productCreationQueueName;

    @Value("product.list.queue.name")
    private String productListQueueName;

    @Bean
    Queue productCreationQueue() {
        return QueueBuilder.durable(productCreationQueueName).build();
    }

    @Bean
    Queue productListQueue() {
        return QueueBuilder.durable(productListQueueName).build();
    }
}
