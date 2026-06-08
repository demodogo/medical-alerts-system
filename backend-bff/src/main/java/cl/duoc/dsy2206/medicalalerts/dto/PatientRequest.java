package cl.duoc.dsy2206.medicalalerts.dto;

import cl.duoc.dsy2206.medicalalerts.entity.PatientStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PatientRequest {

 @NotBlank(message = "El nombre es requerido")
 @Size(max = 120, message = "El nombre no puede exceder los 120 caracteres")
 private String fullName;

 @NotBlank(message = "El identificador clínico es requerido")
 @Size(max = 50, message = "Identificador clínico incorrecto")
 private String clinicalId;

 @NotBlank(message = "La habitación es requerida")
 @Size(max = 50, message = "La habitación no puede exceder los 20 caracteres")
 private String room;

 private PatientStatus status;

 public String getFullName() {
  return fullName;
 }

 public String getClinicalId() {
  return clinicalId;
 }

 public String getRoom() {
  return room;
 }

 public PatientStatus getStatus() {
  return status;
 }

 public void setFullName(String fullName) {
  this.fullName = fullName;
 }

 public void setClinicalId(String clinicalId) {
  this.clinicalId = clinicalId;
 }

 public void setRoom(String room) {
  this.room = room;
 }

 public void setStatus(PatientStatus status) {
  this.status = status;
 }
}
