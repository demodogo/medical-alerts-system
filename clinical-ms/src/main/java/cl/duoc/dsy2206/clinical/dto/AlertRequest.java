package cl.duoc.dsy2206.clinical.dto;

import cl.duoc.dsy2206.clinical.entity.AlertSeverity;
import cl.duoc.dsy2206.clinical.entity.AlertStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class AlertRequest {

 @NotNull(message = "Id de paciente requerido")
 private Long patientId;

 @NotBlank(message = "Tipo de alerta requerido")
 @Size(max = 50, message = "El tipo de alerta no debe exceder 50 caracteres")
 private String alertType;

 @NotNull(message = "Nivel de severidad requerido")
 private AlertSeverity severity;

 @NotBlank(message = "Mensaje requerido")
 @Size(max = 255, message = "El mensaje no debe exceder 255 caracteres")
 private String message;

 private AlertStatus status;

 public Long getPatientId() {
  return patientId;
 }

 public String getAlertType() {
  return alertType;
 }

 public AlertSeverity getSeverity() {
  return severity;
 }

 public String getMessage() {
  return message;
 }

 public AlertStatus getStatus() {
  return status;
 }

 public void setPatientId(Long patientId) {
  this.patientId = patientId;
 }

 public void setAlertType(String alertType) {
  this.alertType = alertType;
 }

 public void setSeverity(AlertSeverity severity) {
  this.severity = severity;
 }

 public void setMessage(String message) {
  this.message = message;
 }

 public void setStatus(AlertStatus status) {
  this.status = status;
 }
}
