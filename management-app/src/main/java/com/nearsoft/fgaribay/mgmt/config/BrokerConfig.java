package com.nearsoft.fgaribay.mgmt.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BrokerConfig {

    private ProductProperties.RoutingKeys queueProperties;
    private ProductProperties.Exchanges exchangeProperties;

    @Autowired
    public BrokerConfig(ProductProperties properties) {
        this.queueProperties = properties.getQueues();
        this.exchangeProperties = properties.getExchanges();
    }

    @Bean
    public DirectExchange productCreationExchange() {
        return new DirectExchange(exchangeProperties.getCreationName());
    }

    @Bean
    public DirectExchange productListExchange() {
        return new DirectExchange(exchangeProperties.getListName());
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
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
