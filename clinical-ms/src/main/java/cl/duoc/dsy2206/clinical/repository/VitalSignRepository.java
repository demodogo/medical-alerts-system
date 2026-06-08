package cl.duoc.dsy2206.clinical.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.duoc.dsy2206.clinical.entity.VitalSign;

public interface VitalSignRepository extends JpaRepository<VitalSign, Long> {
 List<VitalSign> findByPatientIdOrderByMeasuredAtDesc(Long patientId);
}
