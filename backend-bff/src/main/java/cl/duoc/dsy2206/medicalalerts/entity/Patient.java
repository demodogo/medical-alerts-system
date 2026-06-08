package cl.duoc.dsy2206.medicalalerts.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "patients")
public class Patient {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

 @Column(name = "full_name", nullable = false, length = 120)
 private String fullName;

 @Column(name = "clinical_id", nullable = false, unique = true, length = 50)
 private String clinicalId;

 @Column(name = "room", nullable = false, length = 20)
 private String room;

 @Column(name = "status", nullable = false, length = 20)
 private PatientStatus status = PatientStatus.STABLE;

 @Column(name = "created_at", nullable = false, updatable = false)
 private LocalDateTime createdAt;


 @PrePersist
 public void prePersist() {
  if (createdAt == null) {
   createdAt = LocalDateTime.now();
  }

  if (status == null) {
   status = PatientStatus.STABLE;
  }
 }

 public Long getId() {
  return id;
 }

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

 public LocalDateTime getCreatedAt() {
  return createdAt;
 }

 public void setId(Long id) {
  this.id = id;
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

 public void setCreatedAt(LocalDateTime createdAt) {
  this.createdAt = createdAt;
 }

}
