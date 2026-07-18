package com.dsy2206.kafkavitalsignsprocessor.service;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Instant;
import org.springframework.stereotype.Service;

import com.dsy2206.kafkavitalsignsprocessor.event.VitalSignsEvent;
import com.dsy2206.kafkavitalsignsprocessor.event.AlertEvent;
import com.dsy2206.kafkavitalsignsprocessor.service.AlertPublisher;

@Service
public class VitalSignsProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(VitalSignsProcessor.class);

    private static final double MIN_HEART_RATE = 60.0;
    private static final double MAX_HEART_RATE = 100.0;

    private static final double MIN_SYSTOLIC_PRESSURE = 90.0;
    private static final double MAX_SYSTOLIC_PRESSURE = 140.0;

    private static final double MIN_DIASTOLIC_PRESSURE = 60.0;
    private static final double MAX_DIASTOLIC_PRESSURE = 90.0;

    private static final double MIN_TEMPERATURE = 36.1;
    private static final double MAX_TEMPERATURE = 37.5;

    private final AlertPublisher alertPublisher;

    public VitalSignsProcessor(AlertPublisher alertPublisher) {
        this.alertPublisher = alertPublisher;
    }

    public void process(VitalSignsEvent event) {
        if (!isValid(event)) {
            LOGGER.warn("Vital sign inválido. Ignorando evento: {}", event);
            return;
        }

        LOGGER.info("Procesando evento de signos vitales: eventId={}, patientId={}", event.eventId(), event.patientId());
        
        evaluate(event, "HEART_RATE", event.heartRate().doubleValue(), MIN_HEART_RATE, MAX_HEART_RATE);
        evaluate(event, "SYSTOLIC_PRESSURE", event.systolicPressure().doubleValue(), MIN_SYSTOLIC_PRESSURE, MAX_SYSTOLIC_PRESSURE);
        evaluate(event, "DIASTOLIC_PRESSURE", event.diastolicPressure().doubleValue(), MIN_DIASTOLIC_PRESSURE, MAX_DIASTOLIC_PRESSURE);
        evaluate(event, "TEMPERATURE", event.temperature().doubleValue(), MIN_TEMPERATURE, MAX_TEMPERATURE);
    }

    private void evaluate(VitalSignsEvent sourceEvent, String vitalSign, double measuredValue, double minValue, double maxValue) {
        if (measuredValue >= minValue && measuredValue <= maxValue) {
            return;
        }

        String direction = measuredValue < minValue ? "LOW" : "HIGH";

        AlertEvent alert = new AlertEvent(
            UUID.randomUUID(),
            sourceEvent.eventId(),
            sourceEvent.patientId(),
            vitalSign + "_" + direction,
            measuredValue,
            minValue,
            maxValue,
            Instant.now()
        );

        alertPublisher.publishAlert(alert);
    }

    private boolean isValid(VitalSignsEvent event) {
        return event != null &&
        event.eventId() != null &&
        event.heartRate() != null &&
        event.systolicPressure() != null &&
        event.diastolicPressure() != null &&
        event.temperature() != null &&
        event.patientId() != null &&
        event.measuredAt() != null;
    }
}