package com.dsy2206.kafkaalertoracleconsumer.event;

import java.time.Instant;
import java.util.UUID;

public record AlertEvent (
    UUID alertId,
    UUID sourceEventId,
    Long patientId,
    String anomalyType,
    Double measuredValue,
    Double minimumThreshold,
    Double maximumThreshold,
    Instant detectedAt
){
}
