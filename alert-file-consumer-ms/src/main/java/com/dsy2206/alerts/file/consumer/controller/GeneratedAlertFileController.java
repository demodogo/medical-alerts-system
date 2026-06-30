package com.dsy2206.alerts.file.consumer.controller;
import java.io.IOException;
import java.util.List;
import com.dsy2206.alerts.file.consumer.service.AlertFileConsumerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/generated-alert-files")
@RequiredArgsConstructor
public class GeneratedAlertFileController {

    private final AlertFileConsumerService alertFileConsumerService;

    @GetMapping
    public ResponseEntity<List<String>> listFiles() throws IOException {
        return ResponseEntity.ok(alertFileConsumerService.listFiles());
    }

    @GetMapping(value = "/{filename}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> readFile(@PathVariable String filename) throws IOException {
        return ResponseEntity.ok(alertFileConsumerService.readFile(filename));
    }

    @DeleteMapping("/{filename}")
    public ResponseEntity<Void> deleteFile(@PathVariable String filename) throws IOException {
        alertFileConsumerService.deleteFile(filename);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/health-check")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("alert-file-consumer-ms is running");
    }
}