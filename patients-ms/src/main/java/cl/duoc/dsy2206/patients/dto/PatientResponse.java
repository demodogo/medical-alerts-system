package cl.duoc.dsy2206.patients.dto;

import java.time.LocalDateTime;

import cl.duoc.dsy2206.patients.entity.Patient;
import cl.duoc.dsy2206.patients.entity.PatientStatus;

public class PatientResponse {

  private final Long id;
  private final String fullName;
  private final String clinicalIdentifier;
  private final String room;
  private final PatientStatus status;
  private final LocalDateTime createdAt;

  public PatientResponse(Long id, String fullName, String clinicalIdentifier, String room,
      PatientStatus status, LocalDateTime createdAt) {
    this.id = id;
    this.fullName = fullName;
    this.clinicalIdentifier = clinicalIdentifier;
    this.room = room;
    this.status = status;
    this.createdAt = createdAt;
  }

  public Long getId() {
    return id;
  }

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

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public static PatientResponse fromEntity(Patient patient) {
    return new PatientResponse(patient.getId(), patient.getFullName(),
        patient.getClinicalIdentifier(), patient.getRoom(), patient.getStatus(),
        patient.getCreatedAt());
  }
}
