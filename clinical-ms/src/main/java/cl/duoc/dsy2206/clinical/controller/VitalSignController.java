package cl.duoc.dsy2206.clinical.controller;

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

import cl.duoc.dsy2206.clinical.dto.VitalSignRequest;
import cl.duoc.dsy2206.clinical.dto.VitalSignResponse;
import cl.duoc.dsy2206.clinical.service.VitalSignService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/vital-signs")
public class VitalSignController {

 private final VitalSignService vitalSignService;

 public VitalSignController(VitalSignService vitalSignService) {
  this.vitalSignService = vitalSignService;
 }

 @GetMapping
 public List<VitalSignResponse> findAll() {
  return vitalSignService.findAll();
 }

 @GetMapping("/{id}")
 public VitalSignResponse findById(@PathVariable Long id) {
  return vitalSignService.findById(id);
 }

 @GetMapping("/patient/{patientId}")
 public List<VitalSignResponse> findByPatientId(@PathVariable Long patientId) {
  return vitalSignService.findByPatientId(patientId);
 }

 @PostMapping
 @ResponseStatus(HttpStatus.CREATED)
 public VitalSignResponse create(@Valid @RequestBody VitalSignRequest request) {
  return vitalSignService.create(request);
 }

 @PutMapping("/{id}")
 public VitalSignResponse update(@PathVariable Long id,
   @Valid @RequestBody VitalSignRequest request) {
  return vitalSignService.update(id, request);
 }

 @DeleteMapping("/{id}")
 @ResponseStatus(HttpStatus.NO_CONTENT)
 public void delete(@PathVariable Long id) {
  vitalSignService.delete(id);
 }
}
