package com.dsy2206.vitalsigns.producer.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VitalSignsReadingRequest {
 @NotNull
 private Long patientId;

 @NotNull
 @Min(0)
 @Max(250)
 private Integer heartRate;

 @NotNull
 @Min(0)
 @Max(100)
 private Integer oxygenSaturation;

 @NotNull
 private Double temperature;

 @NotNull
 private Integer systolicPressure;

 @NotNull
 private Integer diastolicPressure;

}
