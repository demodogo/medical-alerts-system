package com.dsy2206.kafkaalertoracleconsumer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dsy2206.kafkaalertoracleconsumer.entity.MedicalAlert;

public interface MedicalAlertRepository extends JpaRepository<MedicalAlert, Long> {
    boolean existsByAlertId(String alertId);
    List<MedicalAlert> findByPatientIdOrderByDetectedAtDesc(Long patientId);
}
