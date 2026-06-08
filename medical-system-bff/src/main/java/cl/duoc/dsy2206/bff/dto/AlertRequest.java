package cl.duoc.dsy2206.bff.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class AlertRequest {

 @NotNull(message = "Id de paciente requerida")
 private Long patientId;

 @NotBlank(message = "Tipo de alerta requerida")
 @Size(max = 50)
 private String alertType;

 @NotBlank(message = "Severidad de alerta requerida")
 private String severity;

 @NotBlank(message = "El mensaje es requerido")
 @Size(max = 255)
 private String message;

 private String status;

 public Long getPatientId() {
  return patientId;
 }

 public String getAlertType() {
  return alertType;
 }

 public String getSeverity() {
  return severity;
 }

 public String getMessage() {
  return message;
 }

 public String getStatus() {
  return status;
 }

 public void setPatientId(Long patientId) {
  this.patientId = patientId;
 }

 public void setAlertType(String alertType) {
  this.alertType = alertType;
 }

 public void setSeverity(String severity) {
  this.severity = severity;
 }

 public void setMessage(String message) {
  this.message = message;
 }

 public void setStatus(String status) {
  this.status = status;
 }
}
