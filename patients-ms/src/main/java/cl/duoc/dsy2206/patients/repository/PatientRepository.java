package cl.duoc.dsy2206.patients.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.duoc.dsy2206.patients.entity.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {
 boolean existsByClinicalIdentifier(String clinicalIdentifier);

 boolean existsByClinicalIdentifierAndIdNot(String clinicalIdentifier, Long id);

}
