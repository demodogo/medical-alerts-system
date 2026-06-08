package cl.duoc.dsy2206.clinical.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import cl.duoc.dsy2206.clinical.entity.VitalSign;

public class VitalSignResponse {

 private Long id;
 private Long patientId;
 private Integer heartRate;
 private Integer oxygenSaturation;
 private BigDecimal temperature;
 private Integer systolicPressure;
 private Integer diastolicPressure;
 private LocalDateTime measuredAt;

 public VitalSignResponse(Long id, Long patientId, Integer heartRate, Integer oxygenSaturation,
   BigDecimal temperature, Integer systolicPressure, Integer diastolicPressure,
   LocalDateTime measuredAt) {
  this.id = id;
  this.patientId = patientId;
  this.heartRate = heartRate;
  this.oxygenSaturation = oxygenSaturation;
  this.temperature = temperature;
  this.systolicPressure = systolicPressure;
  this.diastolicPressure = diastolicPressure;
  this.measuredAt = measuredAt;
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


 public static VitalSignResponse fromEntity(VitalSign vitalSign) {
  return new VitalSignResponse(vitalSign.getId(), vitalSign.getPatientId(),
    vitalSign.getHeartRate(), vitalSign.getOxygenSaturation(), vitalSign.getTemperature(),
    vitalSign.getSystolicPressure(), vitalSign.getDiastolicPressure(), vitalSign.getMeasuredAt());
 }
}
