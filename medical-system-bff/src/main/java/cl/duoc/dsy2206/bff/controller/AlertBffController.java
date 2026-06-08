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
import cl.duoc.dsy2206.bff.dto.AlertRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/alerts")
public class AlertBffController {

 private final ClinicalClient clinicalClient;

 public AlertBffController(ClinicalClient clinicalClient) {
  this.clinicalClient = clinicalClient;
 }

 @GetMapping
 public List<Map<String, Object>> findAll() {
  return clinicalClient.findAllAlerts();
 }

 @GetMapping("/{id}")
 public Map<String, Object> findById(@PathVariable Long id) {
  return clinicalClient.findAlertById(id);
 }

 @GetMapping("/patient/{patientId}")
 public List<Map<String, Object>> findByPatientId(@PathVariable Long patientId) {
  return clinicalClient.findAlertsByPatientId(patientId);
 }

 @PostMapping
 @ResponseStatus(HttpStatus.CREATED)
 public Map<String, Object> create(@Valid @RequestBody AlertRequest request) {
  return clinicalClient.createAlert(request);
 }

 @PutMapping("/{id}")
 public Map<String, Object> update(@PathVariable Long id,
   @Valid @RequestBody AlertRequest request) {
  return clinicalClient.updateAlert(id, request);
 }

 @DeleteMapping("/{id}")
 @ResponseStatus(HttpStatus.NO_CONTENT)
 public void delete(@PathVariable Long id) {
  clinicalClient.deleteAlert(id);
 }
}
