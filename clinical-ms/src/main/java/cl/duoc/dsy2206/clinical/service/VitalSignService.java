package cl.duoc.dsy2206.clinical.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.duoc.dsy2206.clinical.dto.VitalSignRequest;
import cl.duoc.dsy2206.clinical.dto.VitalSignResponse;
import cl.duoc.dsy2206.clinical.entity.VitalSign;
import cl.duoc.dsy2206.clinical.exception.ResourceNotFoundException;
import cl.duoc.dsy2206.clinical.repository.VitalSignRepository;

@Service
public class VitalSignService {

 private final VitalSignRepository vitalSignRepository;

 public VitalSignService(VitalSignRepository vitalSignRepository) {
  this.vitalSignRepository = vitalSignRepository;
 }

 public List<VitalSignResponse> findAll() {
  return vitalSignRepository.findAll().stream().map(VitalSignResponse::fromEntity).toList();
 }

 public VitalSignResponse findById(Long id) {
  VitalSign vitalSign = getVitalSignOrThrow(id);
  return VitalSignResponse.fromEntity(vitalSign);
 }

 public List<VitalSignResponse> findByPatientId(Long patientId) {
  return vitalSignRepository.findByPatientIdOrderByMeasuredAtDesc(patientId).stream()
    .map(VitalSignResponse::fromEntity).toList();
 }

 public VitalSignResponse create(VitalSignRequest request) {
  VitalSign vitalSign = new VitalSign();
  vitalSign.setPatientId(request.getPatientId());
  vitalSign.setHeartRate(request.getHeartRate());
  vitalSign.setOxygenSaturation(request.getOxygenSaturation());
  vitalSign.setTemperature(request.getTemperature());
  vitalSign.setSystolicPressure(request.getSystolicPressure());
  vitalSign.setDiastolicPressure(request.getDiastolicPressure());

  VitalSign savedVitalSign = vitalSignRepository.save(vitalSign);

  return VitalSignResponse.fromEntity(savedVitalSign);
 }

 public VitalSignResponse update(Long id, VitalSignRequest request) {
  VitalSign vitalSign = getVitalSignOrThrow(id);
  vitalSign.setPatientId(request.getPatientId());
  vitalSign.setHeartRate(request.getHeartRate());
  vitalSign.setOxygenSaturation(request.getOxygenSaturation());
  vitalSign.setTemperature(request.getTemperature());
  vitalSign.setSystolicPressure(request.getSystolicPressure());
  vitalSign.setDiastolicPressure(request.getDiastolicPressure());

  VitalSign updatedVitalSign = vitalSignRepository.save(vitalSign);

  return VitalSignResponse.fromEntity(updatedVitalSign);
 }

 public void delete(Long id) {
  VitalSign vitalSign = getVitalSignOrThrow(id);
  vitalSignRepository.delete(vitalSign);
 }

 private VitalSign getVitalSignOrThrow(Long id) {
  return vitalSignRepository.findById(id)
    .orElseThrow(() -> new ResourceNotFoundException("Registro no encontrado"));
 }
}
