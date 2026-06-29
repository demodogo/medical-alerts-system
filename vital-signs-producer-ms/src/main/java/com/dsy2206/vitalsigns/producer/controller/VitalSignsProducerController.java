package com.dsy2206.vitalsigns.producer.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dsy2206.vitalsigns.producer.dto.VitalSignsReadingRequest;
import com.dsy2206.vitalsigns.producer.dto.VitalSignsReadingResponse;
import com.dsy2206.vitalsigns.producer.service.VitalSignsProducerService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/device-readings")
@RequiredArgsConstructor
public class VitalSignsProducerController {

 private final VitalSignsProducerService vitalSignsProducerService;

 @PostMapping
 public ResponseEntity<VitalSignsReadingResponse> receiveReading(
   @Valid @RequestBody VitalSignsReadingRequest request) {
  return ResponseEntity.ok(vitalSignsProducerService.processReading(request));
 }

 @GetMapping("/health-check")
 public ResponseEntity<String> healthCheck() {
  return ResponseEntity.ok("vital-signs-producer-ms is running");
 }
}
