package com.dsy2206.kafkavitalsignsproducer.service;

import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.dsy2206.kafkavitalsignsproducer.event.VitalSignsEvent;

@Service
public class VitalSignsGenerator {
    private static final long FIRST_PATIENT_ID = 1001L;
    private static final long LAST_PATIENT_ID = 1005L;

    private final VitalSignsPublisher publisher;

    public VitalSignsGenerator(VitalSignsPublisher publisher) {
        this.publisher = publisher;
    }

    @Scheduled(fixedRateString = "${app.vital-signs.generation-interval-ms}")
    public void generateAndPublish() {
        VitalSignsEvent event = new VitalSignsEvent(
                UUID.randomUUID(),
                randomLong(FIRST_PATIENT_ID, LAST_PATIENT_ID),
                randomInt(45, 150),
                randomInt(85, 180),
                randomInt(50, 110),
                randomTemperature(35.0, 40.5),
                Instant.now()
        );

        publisher.publishVitalSigns(event);
    }


    private int randomInt(int minimum, int maximum) {
    return ThreadLocalRandom.current().nextInt(minimum, maximum + 1);
    }

    private long randomLong(long minimum, long maximum) {
        return ThreadLocalRandom.current().nextLong(minimum, maximum + 1);
    }

    private double randomTemperature(double minimum, double maximum) {
        double value = ThreadLocalRandom.current().nextDouble(minimum, maximum);
        return Math.round(value * 10.0) / 10.0;
    }
}