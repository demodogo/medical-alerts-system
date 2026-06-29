package com.dsy2206.vitalsigns.producer.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

 public static AlertMessage create(Long patientId, String alertType, String severity,
   String message, VitalSignsReadingRequest reading) {
  return AlertMessage.builder().eventId(UUID.randomUUID().toString()).patientId(patientId)
    .alertType(alertType).severity(severity).message(message).heartRate(reading.getHeartRate())
    .oxygenSaturation(reading.getOxygenSaturation()).temperature(reading.getTemperature())
    .systolicPressure(reading.getSystolicPressure())
    .diastolicPressure(reading.getDiastolicPressure()).createdAt(LocalDateTime.now()).build();
 }
}
