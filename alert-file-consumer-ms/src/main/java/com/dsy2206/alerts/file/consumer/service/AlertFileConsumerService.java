package com.dsy2206.alerts.file.consumer.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.dsy2206.alerts.file.consumer.dto.AlertMessage;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlertFileConsumerService {

    private final ObjectMapper objectMapper;

    @Value("${alerts.files.output-dir}")
    private String outputDir;

    @RabbitListener(queues = "${rabbitmq.queue.alerts.file}")
    public void consumeAlert(Message message) {
        try {
            String json = new String(message.getBody(), StandardCharsets.UTF_8);
            AlertMessage alertMessage = objectMapper.readValue(json, AlertMessage.class);

            Path directory = Paths.get(outputDir);
            Files.createDirectories(directory);

            String safeEventId = alertMessage.getEventId().replaceAll("[^a-zA-Z0-9-]", "_");
            String filename = "alert-" + safeEventId + ".json";
            Path filePath = directory.resolve(filename);

            GeneratedAlertFile generatedFile = new GeneratedAlertFile(
                    alertMessage.getEventId(),
                    alertMessage.getPatientId(),
                    alertMessage.getAlertType(),
                    alertMessage.getSeverity(),
                    alertMessage.getMessage(),
                    alertMessage.getHeartRate(),
                    alertMessage.getOxygenSaturation(),
                    alertMessage.getTemperature(),
                    alertMessage.getSystolicPressure(),
                    alertMessage.getDiastolicPressure(),
                    alertMessage.getCreatedAt(),
                    LocalDateTime.now()
            );

            objectMapper.writerWithDefaultPrettyPrinter().writeValue(filePath.toFile(), generatedFile);

            log.info("Archivo JSON generado. eventId={}, path={}",
                    alertMessage.getEventId(),
                    filePath.toAbsolutePath());

        } catch (Exception exception) {
            log.error("Error generando archivo JSON de alerta", exception);
            throw new RuntimeException("Error generando archivo JSON de alerta", exception);
        }
    }

    public List<String> listFiles() throws IOException {
        Path directory = Paths.get(outputDir);

        if (!Files.exists(directory)) {
            return List.of();
        }

        try (var stream = Files.list(directory)) {
            return stream
                    .filter(Files::isRegularFile)
                    .map(path -> path.getFileName().toString())
                    .sorted()
                    .toList();
        }
    }

    public String readFile(String filename) throws IOException {
        Path filePath = Paths.get(outputDir).resolve(filename).normalize();

        if (!filePath.startsWith(Paths.get(outputDir).normalize())) {
            throw new IllegalArgumentException("Invalid filename");
        }

        return Files.readString(filePath);
    }

    public void deleteFile(String filename) throws IOException {
        Path filePath = Paths.get(outputDir).resolve(filename).normalize();

        if (!filePath.startsWith(Paths.get(outputDir).normalize())) {
            throw new IllegalArgumentException("Invalid filename");
        }

        Files.deleteIfExists(filePath);
    }

    private record GeneratedAlertFile(
            String eventId,
            Long patientId,
            String alertType,
            String severity,
            String message,
            Integer heartRate,
            Integer oxygenSaturation,
            Double temperature,
            Integer systolicPressure,
            Integer diastolicPressure,
            LocalDateTime sourceCreatedAt,
            LocalDateTime fileGeneratedAt
    ) {
    }
}