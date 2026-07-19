package com.dsy2206.kafkaalertoracleconsumer.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.dsy2206.kafkaalertoracleconsumer.event.AlertEvent;
import com.dsy2206.kafkaalertoracleconsumer.service.AlertPersistenceService;

@Component
public class AlertConsumer {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(AlertConsumer.class);

    private final AlertPersistenceService persistenceService;

    public AlertConsumer(AlertPersistenceService persistenceService) {
        this.persistenceService = persistenceService;
    }

    @KafkaListener(
            topics = "${app.kafka.topics.alerts}",
            groupId = "${spring.kafka.consumer.group-id}",
            concurrency = "3"
    )
    public void consume(AlertEvent event) {
        if (event == null) {
            LOGGER.warn("Ignorando una alerta que no pudo ser pasada a AlertEvent");
            return;
        }

        LOGGER.info(
                "Alerta consumida. alertId={}, patientId={}, anomalyType={}",
                event.alertId(),
                event.patientId(),
                event.anomalyType()
        );

        persistenceService.store(event);
    }
}