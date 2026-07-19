package com.dsy2206.kafkaalertoracleconsumer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dsy2206.kafkaalertoracleconsumer.entity.MedicalAlert;
import com.dsy2206.kafkaalertoracleconsumer.event.AlertEvent;
import com.dsy2206.kafkaalertoracleconsumer.repository.MedicalAlertRepository;

@Service
public class AlertPersistenceService {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(AlertPersistenceService.class);

    private final MedicalAlertRepository repository;

    public AlertPersistenceService(MedicalAlertRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void store(AlertEvent event) {
        if (!isValid(event)) {
            LOGGER.warn("Ignorando evento inválido: {}", event);
            return;
        }

        String alertId = event.alertId().toString();

        if (repository.existsByAlertId(alertId)) {
            LOGGER.info("Alerta ya almacenada. alertId={}", alertId);
            return;
        }

        MedicalAlert entity = new MedicalAlert(
                alertId,
                event.sourceEventId().toString(),
                event.patientId(),
                event.anomalyType(),
                event.measuredValue(),
                event.minimumThreshold(),
                event.maximumThreshold(),
                event.detectedAt()
        );

        MedicalAlert savedAlert = repository.save(entity);

        LOGGER.info(
                "Alerta almacenada en Oracle. id={}, alertId={}, patientId={}, anomalyType={}",
                savedAlert.getId(),
                savedAlert.getAlertId(),
                savedAlert.getPatientId(),
                savedAlert.getAnomalyType()
        );
    }

    private boolean isValid(AlertEvent event) {
        return event != null
                && event.alertId() != null
                && event.sourceEventId() != null
                && event.patientId() != null
                && event.anomalyType() != null
                && event.measuredValue() != null
                && event.minimumThreshold() != null
                && event.maximumThreshold() != null
                && event.detectedAt() != null;
    }
}