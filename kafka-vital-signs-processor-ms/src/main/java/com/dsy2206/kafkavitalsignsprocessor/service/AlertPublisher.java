package com.dsy2206.kafkavitalsignsprocessor.service;

import com.dsy2206.kafkavitalsignsprocessor.event.AlertEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class AlertPublisher {
    private static final Logger LOGGER = LoggerFactory.getLogger(AlertPublisher.class);

    private final KafkaTemplate<String, AlertEvent> kafkaTemplate;

    private final String alertsTopic;

    public AlertPublisher(
        KafkaTemplate<String, AlertEvent> kafkaTemplate,
        @Value("${app.kafka.topics.alerts}") String alertsTopic
    ) {
        this.kafkaTemplate = kafkaTemplate;
        this.alertsTopic = alertsTopic;
    }

    public void publishAlert(AlertEvent alert) {
        String messageKey = alert.patientId().toString();
        LOGGER.info("Publicando alerta con  patientId: {} a topic: {}", messageKey, alertsTopic);

        kafkaTemplate.send(alertsTopic, messageKey, alert)
        .whenComplete((result, ex) -> {
            if (ex != null) {
                LOGGER.error("Error al publicar alerta con patientId: {} a topic: {}", messageKey, alertsTopic, ex);
                return;
            } else {
                LOGGER.info("Alerta publicada con éxito con patientId: {} a topic: {}", messageKey, alertsTopic);
            }
        });
    }
}