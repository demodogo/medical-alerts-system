package com.dsy2206.summaries.producer.dto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SummaryPublishResponse {
    private boolean published;
    private String eventId;
    private String message;
}