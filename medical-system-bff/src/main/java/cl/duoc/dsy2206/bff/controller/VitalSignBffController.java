package cl.duoc.dsy2206.bff.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.dsy2206.bff.client.ClinicalClient;
import cl.duoc.dsy2206.bff.dto.VitalSignRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/vital-signs")
public class VitalSignBffController {

 private final ClinicalClient clinicalClient;

 public VitalSignBffController(ClinicalClient clinicalClient) {
  this.clinicalClient = clinicalClient;
 }

 @GetMapping
 public List<Map<String, Object>> findAll() {
  return clinicalClient.findAllVitalSigns();
 }

 @GetMapping("/{id}")
 public Map<String, Object> findById(@PathVariable Long id) {
  return clinicalClient.findVitalSignById(id);
 }

 @GetMapping("/patient/{patientId}")
 public List<Map<String, Object>> findByPatientId(@PathVariable Long patientId) {
  return clinicalClient.findVitalSignsByPatientId(patientId);
 }

 @PostMapping
 @ResponseStatus(HttpStatus.CREATED)
 public Map<String, Object> create(@Valid @RequestBody VitalSignRequest request) {
  return clinicalClient.createVitalSign(request);
 }

 @PutMapping("/{id}")
 public Map<String, Object> update(@PathVariable Long id,
   @Valid @RequestBody VitalSignRequest request) {
  return clinicalClient.updateVitalSign(id, request);
 }

 @DeleteMapping("/{id}")
 @ResponseStatus(HttpStatus.NO_CONTENT)
 public void delete(@PathVariable Long id) {
  clinicalClient.deleteVitalSign(id);
 }
}
