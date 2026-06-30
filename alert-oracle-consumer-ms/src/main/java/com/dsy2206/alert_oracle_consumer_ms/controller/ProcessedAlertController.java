package com.dsy2206.alert_oracle_consumer_ms.controller;
import java.util.List;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dsy2206.alert_oracle_consumer_ms.dto.UpdateProcessedAlertRequest;
import com.dsy2206.alert_oracle_consumer_ms.entity.ProcessedAlert;
import com.dsy2206.alert_oracle_consumer_ms.service.AlertOracleConsumerService;

@RestController
@RequestMapping("/api/processed-alerts")
@RequiredArgsConstructor
public class ProcessedAlertController {

    private final AlertOracleConsumerService alertOracleConsumerService;

    @GetMapping
    public ResponseEntity<List<ProcessedAlert>> findAll() {
        return ResponseEntity.ok(alertOracleConsumerService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProcessedAlert> findById(@PathVariable Long id) {
        return ResponseEntity.ok(alertOracleConsumerService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProcessedAlert> updateSeverity(
            @PathVariable Long id,
            @Valid @RequestBody UpdateProcessedAlertRequest request
    ) {
        return ResponseEntity.ok(alertOracleConsumerService.updateSeverity(id, request.getSeverity()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        alertOracleConsumerService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/health-check")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("alert-oracle-consumer-ms is running");
    }
}