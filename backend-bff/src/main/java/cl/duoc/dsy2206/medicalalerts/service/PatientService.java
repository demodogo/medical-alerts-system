package cl.duoc.dsy2206.medicalalerts.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.duoc.dsy2206.medicalalerts.dto.PatientRequest;
import cl.duoc.dsy2206.medicalalerts.dto.PatientResponse;
import cl.duoc.dsy2206.medicalalerts.entity.Patient;
import cl.duoc.dsy2206.medicalalerts.entity.PatientStatus;
import cl.duoc.dsy2206.medicalalerts.exception.ResourceNotFoundException;
import cl.duoc.dsy2206.medicalalerts.repository.PatientRepository;

@Service
public class PatientService {

 private final PatientRepository patientRepository;

 public PatientService(PatientRepository patientRepository) {
  this.patientRepository = patientRepository;
 }

 public List<PatientResponse> findAll() {
  return patientRepository.findAll().stream().map(PatientResponse::fromEntity).toList();
 }

 public PatientResponse findById(Long id) {
  Patient patient = getPatientOrThrow(id);
  return PatientResponse.fromEntity(patient);
 }

 private Patient getPatientOrThrow(Long id) {
  return patientRepository.findById(id)
    .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado"));
 }

 public PatientResponse create(PatientRequest request) {
  if (patientRepository.existsByClinicalId(request.getClinicalId())) {
   throw new IllegalArgumentException("Identificador clínico duplicado");
  }

  Patient patient = new Patient();
  patient.setFullName(request.getFullName());
  patient.setClinicalId(request.getClinicalId());
  patient.setRoom(request.getRoom());
  patient.setStatus(request.getStatus() != null ? request.getStatus() : PatientStatus.CRITICAL);

  Patient savedPatient = patientRepository.save(patient);
  return PatientResponse.fromEntity(savedPatient);
 }

 public PatientResponse update(Long id, PatientRequest request) {
  Patient patient = getPatientOrThrow(id);
  patient.setFullName(request.getFullName());
  patient.setClinicalId(request.getClinicalId());
  patient.setRoom(request.getRoom());
  patient.setStatus(request.getStatus() != null ? request.getStatus() : patient.getStatus());

  Patient updatedPatient = patientRepository.save(patient);

  return PatientResponse.fromEntity(updatedPatient);
 }

 public void delete(Long id) {
  Patient patient = getPatientOrThrow(id);
  patientRepository.delete(patient);
 }
}
