package com.dsy2206.kafkavitalsignsproducer.service;

import com.dsy2206.kafkavitalsignsproducer.event.VitalSignsEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class VitalSignsPublisher {
    private static final Logger LOGGER = LoggerFactory.getLogger(VitalSignsPublisher.class);

    private final KafkaTemplate<String, VitalSignsEvent> kafkaTemplate;
    private final String vitalSignsTopic;

    public VitalSignsPublisher(KafkaTemplate<String, VitalSignsEvent> kafkaTemplate, @Value("${app.kafka.topics.vital-signs}") String vitalSignsTopic) {
        this.kafkaTemplate = kafkaTemplate;
        this.vitalSignsTopic = vitalSignsTopic;
    }

    public void publishVitalSigns(VitalSignsEvent event) {
        LOGGER.info("Publicando vital signs: {}", event);
        String messageKey = event.patientId().toString();
        kafkaTemplate.send(vitalSignsTopic, messageKey, event)
        .whenComplete((result, ex) -> {
            if (ex != null) {
                 LOGGER.error(
                    "Fallo al publicar evento vital-signs. eventId={}, patientId={}",
                    event.eventId(),
                    event.patientId(),
                    ex
                );
                return;
            }
                LOGGER.info(
                    "Evento vital-signs publicado. eventId={}, patientId={}, topic={}, partition={}, offset={}",
                    event.eventId(),
                    event.patientId(),
                    result.getRecordMetadata().topic(),
                    result.getRecordMetadata().partition(),
                    result.getRecordMetadata().offset()
                );
        });
    }
}
