package com.dsy2206.alerts.file.consumer.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.StatelessRetryOperationsInterceptor;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.queue.alerts.file}")
    private String fileQueue;

    @Bean
    public Queue fileAlertQueue() {
        return new Queue(fileQueue, true);
    }

    @Bean
    public StatelessRetryOperationsInterceptor retryInterceptor() {
    return RetryInterceptorBuilder.stateless().maxRetries(3)
        .recoverer(new RejectAndDontRequeueRecoverer()).build();
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }
}