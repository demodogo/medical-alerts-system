package com.dsy2206.summaries.producer.dto;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SummaryPublishRequest {

    @NotNull
    private Long patientId;

    @NotNull
    @Min(0)
    @Max(250)
    private Integer averageHeartRate;

    @NotNull
    @Min(0)
    @Max(100)
    private Integer averageOxygenSaturation;

    @NotNull
    private Double averageTemperature;

    @NotNull
    private Integer averageSystolicPressure;

    @NotNull
    private Integer averageDiastolicPressure;

    private String periodDescription;
}