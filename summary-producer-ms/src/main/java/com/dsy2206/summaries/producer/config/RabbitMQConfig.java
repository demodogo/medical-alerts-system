package com.dsy2206.summaries.producer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.exchange.summaries}")
    private String summariesExchange;

    @Value("${rabbitmq.queue.summaries}")
    private String summariesQueue;

    @Value("${rabbitmq.routing-key.summaries}")
    private String summariesRoutingKey;

    @Bean
    public DirectExchange summariesExchange() {
        return new DirectExchange(summariesExchange, true, false);
    }

    @Bean
    public Queue summariesQueue() {
        return new Queue(summariesQueue, true);
    }

    @Bean
    public Binding summariesBinding() {
        return BindingBuilder
                .bind(summariesQueue())
                .to(summariesExchange())
                .with(summariesRoutingKey);
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
}