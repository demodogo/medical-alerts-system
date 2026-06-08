package cl.duoc.dsy2206.clinical.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.duoc.dsy2206.clinical.entity.Alert;

public interface AlertRepository extends JpaRepository<Alert, Long> {
 List<Alert> findByPatientIdOrderByCreatedAtDesc(Long patientId);
}
