package cl.duoc.dsy2206.bff.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PatientRequest {

 @NotBlank(message = "El nombre es requerido")
 @Size(max = 120)
 private String fullName;

 @NotBlank(message = "Identificador clínico requerido")
 @Size(max = 50)
 private String clinicalIdentifier;

 @NotBlank(message = "Código de habitación requerido")
 @Size(max = 20)
 private String room;

 private String status;

 public String getFullName() {
  return fullName;
 }

 public String getClinicalIdentifier() {
  return clinicalIdentifier;
 }

 public String getRoom() {
  return room;
 }

 public String getStatus() {
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

 public void setStatus(String status) {
  this.status = status;
 }
}
