package cl.duoc.dsy2206.patients.dto;

import cl.duoc.dsy2206.patients.entity.PatientStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PatientRequest {

 @NotBlank(message = "El nombre es requerido")
 @Size(max = 120, message = "El nombre no puede exceder los 120 caracteres")
 private String fullName;

 @NotBlank(message = "Identificador clínico requerido")
 @Size(max = 50, message = "El identificador clínico no puede exceder los 50 caracteres")
 private String clinicalIdentifier;

 @NotBlank(message = "Habitación requerida")
 @Size(max = 20, message = "Código habitación no puede exceder los 20 caractéres")
 private String room;

 private PatientStatus status;

 public String getFullName() {
  return fullName;
 }

 public String getClinicalIdentifier() {
  return clinicalIdentifier;
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

 public void setClinicalIdentifier(String clinicalIdentifier) {
  this.clinicalIdentifier = clinicalIdentifier;
 }

 public void setRoom(String room) {
  this.room = room;
 }

 public void setStatus(PatientStatus status) {
  this.status = status;
 }
}
