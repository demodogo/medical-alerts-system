package cl.duoc.dsy2206.clinical.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class VitalSignRequest {

 @NotNull(message = "Id de paciente requerido")
 private Long patientId;

 @NotNull(message = "Ritmo cardiaco obligatorio")
 @Min(value = 1, message = "El ritmo cardiaco debe ser mayor a 0")
 private Integer heartRate;

 @NotNull(message = "Saturación de oxigeno requerida")
 @Min(value = 1, message = "La saturación de oxigeno debe ser mayor a 0")
 @Max(value = 100, message = "La saturación de oxigeno no debe exceder 100")
 private Integer oxygenSaturation;

 @NotNull(message = "Temperatura requerida")
 @DecimalMin(value = "25.0", message = "La temperatura debe ser mayor o igual a 25.0")
 @DecimalMax(value = "45.0", message = "La temperatura no debe exceder 45.0")
 private BigDecimal temperature;

 @NotNull(message = "Presión sistólica requerida")
 @Min(value = 1, message = "La presión sistólica debe ser mayor a 0")
 private Integer systolicPressure;

 @NotNull(message = "Presión diastólica requerida")
 @Min(value = 1, message = "La presión diastólica debe ser mayor a 0")
 private Integer diastolicPressure;

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
}
