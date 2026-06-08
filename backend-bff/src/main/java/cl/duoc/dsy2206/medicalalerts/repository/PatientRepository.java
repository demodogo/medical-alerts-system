package cl.duoc.dsy2206.medicalalerts.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.duoc.dsy2206.medicalalerts.entity.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {
 boolean existsByClinicalId(String clinicalId);
}
