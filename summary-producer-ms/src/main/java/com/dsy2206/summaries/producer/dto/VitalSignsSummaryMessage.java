package com.dsy2206.summaries.producer.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VitalSignsSummaryMessage {

    private String eventId;
    private Long patientId;

    private Integer averageHeartRate;
    private Integer averageOxygenSaturation;
    private Double averageTemperature;
    private Integer averageSystolicPressure;
    private Integer averageDiastolicPressure;

    private String periodDescription;
    private LocalDateTime createdAt;

    public static VitalSignsSummaryMessage fromRequest(SummaryPublishRequest request) {
        return VitalSignsSummaryMessage.builder()
                .eventId(UUID.randomUUID().toString())
                .patientId(request.getPatientId())
                .averageHeartRate(request.getAverageHeartRate())
                .averageOxygenSaturation(request.getAverageOxygenSaturation())
                .averageTemperature(request.getAverageTemperature())
                .averageSystolicPressure(request.getAverageSystolicPressure())
                .averageDiastolicPressure(request.getAverageDiastolicPressure())
                .periodDescription(request.getPeriodDescription())
                .createdAt(LocalDateTime.now())
                .build();
    }
}