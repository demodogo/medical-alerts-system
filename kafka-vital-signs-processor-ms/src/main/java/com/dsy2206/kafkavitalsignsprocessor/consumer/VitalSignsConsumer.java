package com.dsy2206.kafkavitalsignsprocessor.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.dsy2206.kafkavitalsignsprocessor.event.VitalSignsEvent;
import com.dsy2206.kafkavitalsignsprocessor.service.VitalSignsProcessor;

@Component
public class VitalSignsConsumer{
    private static final Logger LOGGER = LoggerFactory.getLogger(VitalSignsConsumer.class);
    
    private final VitalSignsProcessor processor;

    public VitalSignsConsumer(VitalSignsProcessor processor) {
        this.processor = processor;
    }

    @KafkaListener(
        topics = "${app.kafka.topics.vital-signs}", 
        groupId = "${spring.kafka.consumer.group-id}",
        concurrency = "3")
    public void consume(VitalSignsEvent event) {
        LOGGER.info("Consumiendo evento de signos vitales con id: {}, y con patientId: {}", event.eventId(), event.patientId());
        processor.process(event);
    }
}