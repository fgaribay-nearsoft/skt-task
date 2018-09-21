package com.nearsoft.fgaribay.mgmt.config;

import com.nearsoft.fgaribay.mgmt.config.properties.ExchangeProperties;
import com.nearsoft.fgaribay.mgmt.config.properties.QueueProperties;
import com.nearsoft.fgaribay.mgmt.config.properties.RoutingKeyProperties;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class BrokerConfig {

  private QueueProperties queueProperties;
  private ExchangeProperties exchangeProperties;
  private RoutingKeyProperties routingKeyProperties;

  public BrokerConfig(
      QueueProperties queueProperties,
      ExchangeProperties exchangeProperties,
      RoutingKeyProperties routingKeyProperties) {
    this.queueProperties = queueProperties;
    this.exchangeProperties = exchangeProperties;
    this.routingKeyProperties = routingKeyProperties;
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
