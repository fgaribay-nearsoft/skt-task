package com.nearsoft.fgaribay.mgmt.microservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BrokerConfig {

  private ProductProperties.Queues queueProperties;
  private ProductProperties.Exchanges exchangeProperties;
  private ProductProperties.RoutingKeys routingKeyProperties;

  @Autowired
  public BrokerConfig(ProductProperties properties) {
    this.queueProperties = properties.getQueues();
    this.exchangeProperties = properties.getExchanges();
    this.routingKeyProperties = properties.getRoutingKeys();
  }

  @Bean
  public Queue productCreationQueue() {
    return QueueBuilder.durable(queueProperties.getCreationName()).build();
  }

  @Bean
  public Queue productListQueue() {
    return QueueBuilder.durable(queueProperties.getListName()).build();
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
  public Binding productCreationBinding(
      DirectExchange productCreationExchange, Queue productCreationQueue) {
    return BindingBuilder.bind(productCreationQueue)
        .to(productCreationExchange)
        .with(routingKeyProperties.getCreationName());
  }

  @Bean
  public Binding productListBinding(DirectExchange productListExchange, Queue productListQueue) {
    return BindingBuilder.bind(productListQueue)
        .to(productListExchange)
        .with(routingKeyProperties.getListName());
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
}
