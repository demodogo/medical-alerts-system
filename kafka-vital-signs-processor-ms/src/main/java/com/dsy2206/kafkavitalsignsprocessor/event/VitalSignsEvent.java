package com.dsy2206.kafkavitalsignsprocessor.event;
import java.time.Instant;
import java.util.UUID;

public record VitalSignsEvent (
    UUID eventId,
    Long patientId,
    Integer heartRate,
    Integer systolicPressure,
    Integer diastolicPressure,
    Double temperature,
    Instant measuredAt
) {
}
