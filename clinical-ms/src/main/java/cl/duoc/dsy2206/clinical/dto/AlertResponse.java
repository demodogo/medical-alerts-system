package cl.duoc.dsy2206.clinical.dto;

import java.time.LocalDateTime;

import cl.duoc.dsy2206.clinical.entity.Alert;
import cl.duoc.dsy2206.clinical.entity.AlertSeverity;
import cl.duoc.dsy2206.clinical.entity.AlertStatus;

public class AlertResponse {

  private final Long id;
  private final Long patientId;
  private final String alertType;
  private final AlertSeverity severity;
  private final String message;
  private final AlertStatus status;
  private final LocalDateTime createdAt;

  public AlertResponse(Long id, Long patientId, String alertType, AlertSeverity severity,
      String message, AlertStatus status, LocalDateTime createdAt) {
    this.id = id;
    this.patientId = patientId;
    this.alertType = alertType;
    this.severity = severity;
    this.message = message;
    this.status = status;
    this.createdAt = createdAt;
  }

  public Long getId() {
    return id;
  }

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

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public static AlertResponse fromEntity(Alert alert) {
    return new AlertResponse(alert.getId(), alert.getPatientId(), alert.getAlertType(),
        alert.getSeverity(), alert.getMessage(), alert.getStatus(), alert.getCreatedAt());
  }
}
