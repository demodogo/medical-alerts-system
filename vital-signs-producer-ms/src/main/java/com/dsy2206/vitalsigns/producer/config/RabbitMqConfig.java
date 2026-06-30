package com.dsy2206.vitalsigns.producer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.StatelessRetryOperationsInterceptor;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

 @Value("${rabbitmq.exchange.alerts}")
 private String alertsExchange;

 @Value("${rabbitmq.queue.alerts.oracle}")
 private String oracleQueue;

 @Value("${rabbitmq.queue.alerts.file}")
 private String fileQueue;

 @Bean
 public FanoutExchange alertsExchange() {
  return new FanoutExchange(alertsExchange, true, false);
 }


 @Bean
 public Queue oracleAlertQueue() {
  return new Queue(oracleQueue, true);
 }

 @Bean
 public Queue fileAlertQueue() {
  return new Queue(fileQueue, true);
 }

 @Bean
 public Binding oracleAlertBinding() {
  return BindingBuilder.bind(oracleAlertQueue()).to(alertsExchange());
 }

 @Bean
 public Binding fileAlertBinding() {
  return BindingBuilder.bind(fileAlertQueue()).to(alertsExchange());
 }

 @Bean
 public MessageConverter messageConverter() {
  return new JacksonJsonMessageConverter();
 }

 @Bean
 public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
  RabbitTemplate template = new RabbitTemplate(connectionFactory);
  template.setMessageConverter(messageConverter());
  return template;
 }

 @Bean
 public StatelessRetryOperationsInterceptor retryInterceptor() {
  return RetryInterceptorBuilder.stateless().maxRetries(3)
    .recoverer(new RejectAndDontRequeueRecoverer()).build();
 }
}
