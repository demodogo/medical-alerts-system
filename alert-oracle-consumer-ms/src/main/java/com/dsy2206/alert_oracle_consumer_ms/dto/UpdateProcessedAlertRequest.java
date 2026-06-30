package com.dsy2206.alert_oracle_consumer_ms.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateProcessedAlertRequest {

    @NotBlank
    private String severity;
}