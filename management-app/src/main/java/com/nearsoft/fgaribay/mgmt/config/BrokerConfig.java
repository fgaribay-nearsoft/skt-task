package com.nearsoft.fgaribay.mgmt.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BrokerConfig {

    private ProductProperties.Queues queueProperties;

    @Autowired
    public BrokerConfig(ProductProperties properties) {
        this.queueProperties = properties.getQueues();
    }

    @Bean
    Queue productCreationQueue() {
        return QueueBuilder.durable(queueProperties.getCreationName()).build();
    }

    @Bean
    Queue productListQueue() {
        return QueueBuilder.durable(queueProperties.getListName()).build();
    }
}
