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

import cl.duoc.dsy2206.bff.client.PatientsClient;
import cl.duoc.dsy2206.bff.dto.PatientRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/patients")
public class PatientBffController {

 private final PatientsClient patientsClient;

 public PatientBffController(PatientsClient patientsClient) {
  this.patientsClient = patientsClient;
 }

 @GetMapping
 public List<Map<String, Object>> findAll() {
  return patientsClient.findAll();
 }

 @GetMapping("/{id}")
 public Map<String, Object> findById(@PathVariable Long id) {
  return patientsClient.findById(id);
 }

 @PostMapping
 @ResponseStatus(HttpStatus.CREATED)
 public Map<String, Object> create(@Valid @RequestBody PatientRequest request) {
  return patientsClient.create(request);
 }

 @PutMapping("/{id}")
 public Map<String, Object> update(@PathVariable Long id,
   @Valid @RequestBody PatientRequest request) {
  return patientsClient.update(id, request);
 }

 @DeleteMapping("/{id}")
 @ResponseStatus(HttpStatus.NO_CONTENT)
 public void delete(@PathVariable Long id) {
  patientsClient.delete(id);
 }
}
