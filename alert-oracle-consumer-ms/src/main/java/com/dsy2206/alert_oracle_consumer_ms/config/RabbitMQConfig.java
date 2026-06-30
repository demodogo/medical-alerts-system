package com.dsy2206.alert_oracle_consumer_ms.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.StatelessRetryOperationsInterceptor;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.queue.alerts.oracle}")
    private String oracleQueue;

    @Bean
    public Queue oracleAlertQueue() {
        return new Queue(oracleQueue, true);
    }

    @Bean
    public StatelessRetryOperationsInterceptor retryInterceptor() {
    return RetryInterceptorBuilder.stateless().maxRetries(3)
        .recoverer(new RejectAndDontRequeueRecoverer()).build();
    }
}
