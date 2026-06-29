package com.dsy2206.vitalsigns.producer.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VitalSignsReadingResponse {

 private boolean anomalyDetected;
 private String alertType;
 private String severity;
 private String message;
 private String publishedEventId;
}
