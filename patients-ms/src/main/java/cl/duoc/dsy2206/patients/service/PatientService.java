package cl.duoc.dsy2206.patients.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.duoc.dsy2206.patients.dto.PatientRequest;
import cl.duoc.dsy2206.patients.dto.PatientResponse;
import cl.duoc.dsy2206.patients.entity.Patient;
import cl.duoc.dsy2206.patients.entity.PatientStatus;
import cl.duoc.dsy2206.patients.exception.ResourceNotFoundException;
import cl.duoc.dsy2206.patients.repository.PatientRepository;

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

  public PatientResponse create(PatientRequest request) {
    if (patientRepository.existsByClinicalIdentifier(request.getClinicalIdentifier())) {
      throw new IllegalArgumentException("Id clínico duplicado");
    }

    Patient patient = new Patient();
    patient.setFullName(request.getFullName());
    patient.setClinicalIdentifier(request.getClinicalIdentifier());
    patient.setRoom(request.getRoom());
    patient.setStatus(request.getStatus() != null ? request.getStatus() : PatientStatus.CRITICAL);

    Patient savedPatient = patientRepository.save(patient);

    return PatientResponse.fromEntity(savedPatient);
  }

  public PatientResponse update(Long id, PatientRequest request) {
    Patient patient = getPatientOrThrow(id);

    if (patientRepository.existsByClinicalIdentifierAndIdNot(request.getClinicalIdentifier(), id)) {
      throw new IllegalArgumentException("Id clínico duplicado");
    }

    patient.setFullName(request.getFullName());
    patient.setClinicalIdentifier(request.getClinicalIdentifier());
    patient.setRoom(request.getRoom());
    patient.setStatus(request.getStatus() != null ? request.getStatus() : patient.getStatus());

    Patient updatedPatient = patientRepository.save(patient);

    return PatientResponse.fromEntity(updatedPatient);
  }

  public void delete(Long id) {
    Patient patient = getPatientOrThrow(id);
    patientRepository.delete(patient);
  }

  private Patient getPatientOrThrow(Long id) {
    return patientRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado"));
  }
}
