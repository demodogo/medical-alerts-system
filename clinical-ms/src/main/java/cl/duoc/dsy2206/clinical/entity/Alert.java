package cl.duoc.dsy2206.clinical.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "alerts")
public class Alert {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

 @Column(name = "patient_id", nullable = false)
 private Long patientId;

 @Column(name = "alert_type", nullable = false, length = 50)
 private String alertType;

 @Enumerated(EnumType.STRING)
 @Column(name = "severity", nullable = false, length = 20)
 private AlertSeverity severity;

 @Column(name = "message", nullable = false, length = 255)
 private String message;

 @Enumerated(EnumType.STRING)
 @Column(name = "status", nullable = false, length = 20)
 private AlertStatus status = AlertStatus.OPEN;

 @Column(name = "created_at", nullable = false, updatable = false)
 private LocalDateTime createdAt;

 @PrePersist
 public void prePersist() {
  if (createdAt == null) {
   createdAt = LocalDateTime.now();
  }

  if (status == null) {
   status = AlertStatus.OPEN;
  }
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

 public void setId(Long id) {
  this.id = id;
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

 public void setCreatedAt(LocalDateTime createdAt) {
  this.createdAt = createdAt;
 }
}
