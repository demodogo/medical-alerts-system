package com.dsy2206.alerts.file.consumer.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class AlertMessage {
    private String eventId;
    private Long patientId;
    private String alertType;
    private String severity;
    private String message;

    private Integer heartRate;
    private Integer oxygenSaturation;
    private Double temperature;
    private Integer systolicPressure;
    private Integer diastolicPressure;

    private LocalDateTime createdAt;
}