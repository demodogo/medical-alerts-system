package cl.duoc.dsy2206.medicalalerts.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class VitalSignRequest {

 @NotNull(message = "Id de paciente requerido")
 private Long patientId;

 @NotNull(message = "Frecuencia cardiaca requerida")
 @Min(value = 0, message = "La frecuencia cardiaca debe ser mayor o igual a 0")
 @Max(value = 300, message = "La frecuencia cardiaca debe ser menor o igual a 300")
 private Integer heartRate;

 @NotNull(message = "Saturacion de oxigeno requerida")
 @Min(value = 0, message = "La saturacion de oxigeno debe ser mayor o igual a 0")
 @Max(value = 100, message = "La saturacion de oxigeno debe ser menor o igual a 100")
 private Integer oxygenSaturation;

 @NotNull(message = "Temperatura requerida")
 @Min(value = 0, message = "La temperatura debe ser mayor o igual a 0")
 @Max(value = 100, message = "La temperatura debe ser menor o igual a 100")
 private BigDecimal temperature;

 @NotNull(message = "Presion sistólica requerida")
 @Min(value = 0, message = "La presion sistólica debe ser mayor o igual a 0")
 @Max(value = 300, message = "La presion sistólica debe ser menor o igual a 300")
 private Integer systolicPressure;

 @NotNull(message = "Presión diastólica requerida")
 @Min(value = 0, message = "La presión diastólica debe ser mayor o igual a 0")
 @Max(value = 300, message = "La presión diastólica debe ser menor o igual a 300")
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
