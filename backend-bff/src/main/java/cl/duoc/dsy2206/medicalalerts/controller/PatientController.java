package cl.duoc.dsy2206.medicalalerts.controller;

import java.util.List;

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

import cl.duoc.dsy2206.medicalalerts.dto.PatientRequest;
import cl.duoc.dsy2206.medicalalerts.dto.PatientResponse;
import cl.duoc.dsy2206.medicalalerts.service.PatientService;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/patients")
public class PatientController {

 private final PatientService patientService;

 public PatientController(PatientService patientService) {
  this.patientService = patientService;
 }

 @GetMapping
 public List<PatientResponse> getAllPatients() {
  return patientService.findAll();
 }

 @GetMapping("/{id}")
 public PatientResponse findById(@PathVariable Long id) {
  return patientService.findById(id);
 }

 @PostMapping
 @ResponseStatus(HttpStatus.CREATED)
 public PatientResponse create(@Valid @RequestBody PatientRequest request) {
  return patientService.create(request);
 }

 @PutMapping("/{id}")
 public PatientResponse update(@PathVariable Long id, @Valid @RequestBody PatientRequest request) {
  return patientService.update(id, request);
 }

 @DeleteMapping("/{id}")
 public void delete(@PathVariable Long id) {
  patientService.delete(id);
 }
}
