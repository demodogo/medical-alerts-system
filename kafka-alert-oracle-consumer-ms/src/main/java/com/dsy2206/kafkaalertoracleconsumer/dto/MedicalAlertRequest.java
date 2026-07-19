package com.dsy2206.kafkaalertoracleconsumer.dto;

import java.time.Instant;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MedicalAlertRequest(
    @NotNull
    UUID sourceEventId,

    @NotNull
    Long patientId,

    @NotBlank
    @Size(max = 50)
    String anomalyType,

    @NotNull
    Double measuredValue,

    @NotNull
    Double minimumThreshold,

    @NotNull
    Double maximumThreshold,

    @NotNull
    Instant detectedAt
) {
}