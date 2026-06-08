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

import cl.duoc.dsy2206.clinical.dto.AlertRequest;
import cl.duoc.dsy2206.clinical.dto.AlertResponse;
import cl.duoc.dsy2206.clinical.service.AlertService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/alerts")
public class AlertController {

 private final AlertService alertService;

 public AlertController(AlertService alertService) {
  this.alertService = alertService;
 }

 @GetMapping
 public List<AlertResponse> findAll() {
  return alertService.findAll();
 }

 @GetMapping("/{id}")
 public AlertResponse findById(@PathVariable Long id) {
  return alertService.findById(id);
 }

 @GetMapping("/patient/{patientId}")
 public List<AlertResponse> findByPatientId(@PathVariable Long patientId) {
  return alertService.findByPatientId(patientId);
 }

 @PostMapping
 @ResponseStatus(HttpStatus.CREATED)
 public AlertResponse create(@Valid @RequestBody AlertRequest request) {
  return alertService.create(request);
 }

 @PutMapping("/{id}")
 public AlertResponse update(@PathVariable Long id, @Valid @RequestBody AlertRequest request) {
  return alertService.update(id, request);
 }

 @DeleteMapping("/{id}")
 @ResponseStatus(HttpStatus.NO_CONTENT)
 public void delete(@PathVariable Long id) {
  alertService.delete(id);
 }
}
