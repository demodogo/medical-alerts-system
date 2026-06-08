package cl.duoc.dsy2206.clinical.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.duoc.dsy2206.clinical.dto.AlertRequest;
import cl.duoc.dsy2206.clinical.dto.AlertResponse;
import cl.duoc.dsy2206.clinical.entity.Alert;
import cl.duoc.dsy2206.clinical.entity.AlertStatus;
import cl.duoc.dsy2206.clinical.exception.ResourceNotFoundException;
import cl.duoc.dsy2206.clinical.repository.AlertRepository;

@Service
public class AlertService {

 private final AlertRepository alertRepository;

 public AlertService(AlertRepository alertRepository) {
  this.alertRepository = alertRepository;
 }

 public List<AlertResponse> findAll() {
  return alertRepository.findAll().stream().map(AlertResponse::fromEntity).toList();
 }

 public AlertResponse findById(Long id) {
  Alert alert = getAlertOrThrow(id);
  return AlertResponse.fromEntity(alert);
 }

 public List<AlertResponse> findByPatientId(Long patientId) {
  return alertRepository.findByPatientIdOrderByCreatedAtDesc(patientId).stream()
    .map(AlertResponse::fromEntity).toList();
 }

 public AlertResponse create(AlertRequest request) {
  Alert alert = new Alert();
  alert.setPatientId(request.getPatientId());
  alert.setAlertType(request.getAlertType());
  alert.setSeverity(request.getSeverity());
  alert.setMessage(request.getMessage());
  alert.setStatus(request.getStatus() != null ? request.getStatus() : AlertStatus.OPEN);

  Alert savedAlert = alertRepository.save(alert);

  return AlertResponse.fromEntity(savedAlert);
 }

 public AlertResponse update(Long id, AlertRequest request) {
  Alert alert = getAlertOrThrow(id);
  alert.setPatientId(request.getPatientId());
  alert.setAlertType(request.getAlertType());
  alert.setSeverity(request.getSeverity());
  alert.setMessage(request.getMessage());
  alert.setStatus(request.getStatus() != null ? request.getStatus() : alert.getStatus());

  Alert updatedAlert = alertRepository.save(alert);

  return AlertResponse.fromEntity(updatedAlert);
 }

 public void delete(Long id) {
  Alert alert = getAlertOrThrow(id);
  alertRepository.delete(alert);
 }

 private Alert getAlertOrThrow(Long id) {
  return alertRepository.findById(id)
    .orElseThrow(() -> new ResourceNotFoundException("Registro no encontrado"));
 }
}
