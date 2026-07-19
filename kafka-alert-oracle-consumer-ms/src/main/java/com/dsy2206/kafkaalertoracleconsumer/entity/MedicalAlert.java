package com.dsy2206.kafkaalertoracleconsumer.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(
        name = "KAFKA_MEDICAL_ALERTS",
        indexes = {
                @Index(
                        name = "IDX_KAFKA_ALERT_PATIENT",
                        columnList = "PATIENT_ID"
                ),
                @Index(
                        name = "IDX_KAFKA_ALERT_DETECTED",
                        columnList = "DETECTED_AT"
                )
        }
)
public class MedicalAlert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(
            name = "ALERT_ID",
            nullable = false,
            unique = true,
            length = 36
    )
    private String alertId;

    @Column(
            name = "SOURCE_EVENT_ID",
            nullable = false,
            length = 36
    )
    private String sourceEventId;

    @Column(name = "PATIENT_ID", nullable = false)
    private Long patientId;

    @Column(
            name = "ANOMALY_TYPE",
            nullable = false,
            length = 50
    )
    private String anomalyType;

    @Column(name = "MEASURED_VALUE", nullable = false)
    private Double measuredValue;

    @Column(name = "MINIMUM_THRESHOLD", nullable = false)
    private Double minimumThreshold;

    @Column(name = "MAXIMUM_THRESHOLD", nullable = false)
    private Double maximumThreshold;

    @Column(name = "DETECTED_AT", nullable = false)
    private Instant detectedAt;

    @Column(name = "CREATED_AT", nullable = false)
    private Instant createdAt;

    protected MedicalAlert() {
    }

    public MedicalAlert(
            String alertId,
            String sourceEventId,
            Long patientId,
            String anomalyType,
            Double measuredValue,
            Double minimumThreshold,
            Double maximumThreshold,
            Instant detectedAt
    ) {
        this.alertId = alertId;
        this.sourceEventId = sourceEventId;
        this.patientId = patientId;
        this.anomalyType = anomalyType;
        this.measuredValue = measuredValue;
        this.minimumThreshold = minimumThreshold;
        this.maximumThreshold = maximumThreshold;
        this.detectedAt = detectedAt;
    }

    @PrePersist
    public void assignCreatedAt() {
        if (createdAt == null) {
            createdAt = Instant.now();
        }
    }

    public Long getId() {
        return id;
    }

    public String getAlertId() {
        return alertId;
    }

    public void setAlertId(String alertId) {
        this.alertId = alertId;
    }

    public String getSourceEventId() {
        return sourceEventId;
    }

    public void setSourceEventId(String sourceEventId) {
        this.sourceEventId = sourceEventId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getAnomalyType() {
        return anomalyType;
    }

    public void setAnomalyType(String anomalyType) {
        this.anomalyType = anomalyType;
    }

    public Double getMeasuredValue() {
        return measuredValue;
    }

    public void setMeasuredValue(Double measuredValue) {
        this.measuredValue = measuredValue;
    }

    public Double getMinimumThreshold() {
        return minimumThreshold;
    }

    public void setMinimumThreshold(Double minimumThreshold) {
        this.minimumThreshold = minimumThreshold;
    }

    public Double getMaximumThreshold() {
        return maximumThreshold;
    }

    public void setMaximumThreshold(Double maximumThreshold) {
        this.maximumThreshold = maximumThreshold;
    }

    public Instant getDetectedAt() {
        return detectedAt;
    }

    public void setDetectedAt(Instant detectedAt) {
        this.detectedAt = detectedAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}