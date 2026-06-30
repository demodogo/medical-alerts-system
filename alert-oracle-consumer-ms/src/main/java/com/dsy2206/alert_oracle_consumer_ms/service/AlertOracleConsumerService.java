package com.dsy2206.alert_oracle_consumer_ms.service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tools.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import com.dsy2206.alert_oracle_consumer_ms.dto.AlertMessage;
import com.dsy2206.alert_oracle_consumer_ms.entity.ProcessedAlert;
import com.dsy2206.alert_oracle_consumer_ms.repository.ProcessedAlertRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlertOracleConsumerService {

    private final ProcessedAlertRepository processedAlertRepository;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = "${rabbitmq.queue.alerts.oracle}")
    public void consumeAlert(Message message) {
        try {
            String json = new String(message.getBody(), StandardCharsets.UTF_8);
            AlertMessage alertMessage = objectMapper.readValue(json, AlertMessage.class);

            log.info("Alerta recibida desde RabbitMQ. eventId={}, patientId={}, type={}",
                    alertMessage.getEventId(),
                    alertMessage.getPatientId(),
                    alertMessage.getAlertType());

            if (processedAlertRepository.findByEventId(alertMessage.getEventId()).isPresent()) {
                log.warn("Alerta ya procesada. eventId={}", alertMessage.getEventId());
                return;
            }

            ProcessedAlert processedAlert = ProcessedAlert.builder()
                    .eventId(alertMessage.getEventId())
                    .patientId(alertMessage.getPatientId())
                    .alertType(alertMessage.getAlertType())
                    .severity(alertMessage.getSeverity())
                    .message(alertMessage.getMessage())
                    .heartRate(alertMessage.getHeartRate())
                    .oxygenSaturation(alertMessage.getOxygenSaturation())
                    .temperature(alertMessage.getTemperature())
                    .systolicPressure(alertMessage.getSystolicPressure())
                    .diastolicPressure(alertMessage.getDiastolicPressure())
                    .sourceCreatedAt(alertMessage.getCreatedAt())
                    .processedAt(LocalDateTime.now())
                    .build();

            processedAlertRepository.save(processedAlert);

            log.info("Alerta persistida en Oracle. eventId={}, databaseId={}",
                    processedAlert.getEventId(),
                    processedAlert.getId());

        } catch (Exception exception) {
            log.error("Error al consumir el mensaje de alerta desde RabbitMQ", exception);
            throw new RuntimeException("Error al consumir el mensaje de alerta", exception);
        }
    }

    public List<ProcessedAlert> findAll() {
        return processedAlertRepository.findAll();
    }

    public ProcessedAlert findById(Long id) {
        return processedAlertRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Alerta procesada no encontrada con id: " + id));
    }

    public ProcessedAlert updateSeverity(Long id, String severity) {
        ProcessedAlert alert = findById(id);
        alert.setSeverity(severity);
        return processedAlertRepository.save(alert);
    }

    public void deleteById(Long id) {
        if (!processedAlertRepository.existsById(id)) {
            throw new IllegalArgumentException("Alerta procesada no encontrada con id: " + id);
        }

        processedAlertRepository.deleteById(id);
    }
}