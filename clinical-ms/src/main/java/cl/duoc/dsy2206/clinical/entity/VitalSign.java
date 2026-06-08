package cl.duoc.dsy2206.clinical.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "vital_signs")
public class VitalSign {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

 @Column(name = "patient_id", nullable = false)
 private Long patientId;

 @Column(name = "heart_rate", nullable = false)
 private Integer heartRate;

 @Column(name = "oxygen_saturation", nullable = false)
 private Integer oxygenSaturation;

 @Column(name = "temperature", nullable = false, precision = 4, scale = 1)
 private BigDecimal temperature;

 @Column(name = "systolic_pressure", nullable = false)
 private Integer systolicPressure;

 @Column(name = "diastolic_pressure", nullable = false)
 private Integer diastolicPressure;

 @Column(name = "measured_at", nullable = false, updatable = false)
 private LocalDateTime measuredAt;

 @PrePersist
 public void prePersist() {
  if (measuredAt == null) {
   measuredAt = LocalDateTime.now();
  }
 }

 public Long getId() {
  return id;
 }

 public Long getPatientId() {
  return patientId;
 }

 public Integer getHeartRate() {
  return heartRate;
 }

 public Integer getOxygenSaturation() {
  return oxygenSaturation;
 }

 public BigDecimal getTemperature() {
  return temperature;
 }

 public Integer getSystolicPressure() {
  return systolicPressure;
 }

 public Integer getDiastolicPressure() {
  return diastolicPressure;
 }

 public LocalDateTime getMeasuredAt() {
  return measuredAt;
 }

 public void setId(Long id) {
  this.id = id;
 }

 public void setPatientId(Long patientId) {
  this.patientId = patientId;
 }

 public void setHeartRate(Integer heartRate) {
  this.heartRate = heartRate;
 }

 public void setOxygenSaturation(Integer oxygenSaturation) {
  this.oxygenSaturation = oxygenSaturation;
 }

 public void setTemperature(BigDecimal temperature) {
  this.temperature = temperature;
 }

 public void setSystolicPressure(Integer systolicPressure) {
  this.systolicPressure = systolicPressure;
 }

 public void setDiastolicPressure(Integer diastolicPressure) {
  this.diastolicPressure = diastolicPressure;
 }

 public void setMeasuredAt(LocalDateTime measuredAt) {
  this.measuredAt = measuredAt;
 }
}
