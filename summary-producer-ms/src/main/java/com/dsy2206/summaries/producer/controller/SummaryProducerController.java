package com.dsy2206.summaries.producer.controller;
import com.dsy2206.summaries.producer.dto.SummaryPublishRequest;
import com.dsy2206.summaries.producer.dto.SummaryPublishResponse;
import com.dsy2206.summaries.producer.service.SummaryProducerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/summaries")
@RequiredArgsConstructor
public class SummaryProducerController {

    private final SummaryProducerService summaryProducerService;

    @PostMapping("/publish")
    public ResponseEntity<SummaryPublishResponse> publishSummary(
            @Valid @RequestBody SummaryPublishRequest request
    ) {
        return ResponseEntity.ok(summaryProducerService.publishSummary(request));
    }

    @GetMapping("/health-check")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("summary-producer-ms is running");
    }
}