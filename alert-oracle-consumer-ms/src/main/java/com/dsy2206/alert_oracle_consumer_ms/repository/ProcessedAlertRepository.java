package com.dsy2206.alert_oracle_consumer_ms.repository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.dsy2206.alert_oracle_consumer_ms.entity.ProcessedAlert;

public interface ProcessedAlertRepository extends JpaRepository<ProcessedAlert, Long> {
    Optional<ProcessedAlert> findByEventId(String eventId);
}