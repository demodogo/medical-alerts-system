package com.dsy2206.kafkaalertoracleconsumer.service;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.dsy2206.kafkaalertoracleconsumer.dto.MedicalAlertRequest;
import com.dsy2206.kafkaalertoracleconsumer.entity.MedicalAlert;
import com.dsy2206.kafkaalertoracleconsumer.repository.MedicalAlertRepository;

@Service
public class MedicalAlertService {

    private final MedicalAlertRepository repository;

    public MedicalAlertService(MedicalAlertRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<MedicalAlert> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public MedicalAlert findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Alerta no encontrada"
                ));
    }

    @Transactional(readOnly = true)
    public List<MedicalAlert> findByPatientId(Long patientId) {
        return repository.findByPatientIdOrderByDetectedAtDesc(patientId);
    }

    @Transactional
    public MedicalAlert create(MedicalAlertRequest request) {
        MedicalAlert alert = new MedicalAlert(
                UUID.randomUUID().toString(),
                request.sourceEventId().toString(),
                request.patientId(),
                request.anomalyType(),
                request.measuredValue(),
                request.minimumThreshold(),
                request.maximumThreshold(),
                request.detectedAt()
        );

        return repository.save(alert);
    }

    @Transactional
    public MedicalAlert update(Long id, MedicalAlertRequest request) {
        MedicalAlert alert = findById(id);

        alert.setSourceEventId(request.sourceEventId().toString());
        alert.setPatientId(request.patientId());
        alert.setAnomalyType(request.anomalyType());
        alert.setMeasuredValue(request.measuredValue());
        alert.setMinimumThreshold(request.minimumThreshold());
        alert.setMaximumThreshold(request.maximumThreshold());
        alert.setDetectedAt(request.detectedAt());

        return repository.save(alert);
    }

    @Transactional
    public void delete(Long id) {
        MedicalAlert alert = findById(id);
        repository.delete(alert);
    }
}