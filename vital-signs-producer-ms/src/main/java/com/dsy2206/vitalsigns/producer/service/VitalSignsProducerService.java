package com.dsy2206.vitalsigns.producer.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.dsy2206.vitalsigns.producer.dto.AlertMessage;
import com.dsy2206.vitalsigns.producer.dto.VitalSignsReadingRequest;
import com.dsy2206.vitalsigns.producer.dto.VitalSignsReadingResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
@RequiredArgsConstructor
public class VitalSignsProducerService {

 private final RabbitTemplate rabbitTemplate;

 @Value("${rabbitmq.exchange.alerts}")
 private String alertsExchange;

 public VitalSignsReadingResponse processReading(VitalSignsReadingRequest reading) {
  AlertEvaluationResult result = evaluate(reading);

  if (!result.anomalyDetected()) {
   return VitalSignsReadingResponse.builder().anomalyDetected(false).alertType("NORMAL")
     .severity("LOW")
     .message("Signos vitales dentro del rango normal. No se publicó ninguna alerta.")
     .publishedEventId(null).build();
  }

  AlertMessage alertMessage = AlertMessage.create(reading.getPatientId(), result.alertType(),
    result.severity(), result.message(), reading);

  rabbitTemplate.convertAndSend(alertsExchange, "", alertMessage);

  log.info("Mensaje de alerta publicado en RabbitMQ. eventId={}, patientId={}, type={}",
    alertMessage.getEventId(), alertMessage.getPatientId(), alertMessage.getAlertType());

  return VitalSignsReadingResponse.builder().anomalyDetected(true)
    .alertType(alertMessage.getAlertType()).severity(alertMessage.getSeverity())
    .message(alertMessage.getMessage()).publishedEventId(alertMessage.getEventId()).build();
 }

 private AlertEvaluationResult evaluate(VitalSignsReadingRequest reading) {
  if (reading.getOxygenSaturation() < 90) {
   return new AlertEvaluationResult(true, "LOW_OXYGEN", "CRITICAL",
     "La saturación de oxígeno está por debajo del umbral mínimo de seguridad.");
  }

  if (reading.getHeartRate() > 120) {
   return new AlertEvaluationResult(true, "HIGH_HEART_RATE", "HIGH",
     "El ritmo cardíaco está por encima del rango normal.");
  }

  if (reading.getTemperature() >= 38.5) {
   return new AlertEvaluationResult(true, "HIGH_TEMPERATURE", "MEDIUM",
     "La temperatura corporal indica posible fiebre.");
  }

  if (reading.getSystolicPressure() > 140) {
   return new AlertEvaluationResult(true, "HIGH_BLOOD_PRESSURE", "HIGH",
     "La presión arterial sistólica está por encima del rango normal.");
  }

  return new AlertEvaluationResult(false, "NORMAL", "LOW",
    "Signos vitales dentro del rango normal. No se publicó ninguna alerta.");
 }

 private record AlertEvaluationResult(boolean anomalyDetected, String alertType, String severity,
   String message) {
 }
}
