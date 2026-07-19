package com.dsy2206.kafkaalertoracleconsumer.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dsy2206.kafkaalertoracleconsumer.dto.MedicalAlertRequest;
import com.dsy2206.kafkaalertoracleconsumer.entity.MedicalAlert;
import com.dsy2206.kafkaalertoracleconsumer.service.MedicalAlertService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/alerts")
public class MedicalAlertController {

    private final MedicalAlertService service;

    public MedicalAlertController(MedicalAlertService service) {
        this.service = service;
    }

    @GetMapping
    public List<MedicalAlert> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public MedicalAlert findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping("/patient/{patientId}")
    public List<MedicalAlert> findByPatientId(
            @PathVariable Long patientId
    ) {
        return service.findByPatientId(patientId);
    }

    @PostMapping
    public ResponseEntity<MedicalAlert> create(
            @Valid @RequestBody MedicalAlertRequest request
    ) {
        MedicalAlert createdAlert = service.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdAlert);
    }

    @PutMapping("/{id}")
    public MedicalAlert update(
            @PathVariable Long id,
            @Valid @RequestBody MedicalAlertRequest request
    ) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}