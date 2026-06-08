INSERT INTO patients (full_name, clinical_identifier, room, status) VALUES (
  'José García Lara', 'PAC-001', 'UCI-101', 'CRITICAL');


INSERT INTO vital_signs (
  patient_id, heart_rate, oxygen_sturation, temperature, systolic_pressure, diastolic_pressure
) VALUES (
  1, 120, 88, 39.2, 150, 95
);

INSERT INTO alerts(
  patient_id, alert_type, severity, message, status
) VALUES (
  1, 'LOW_OXYGEN', 'HIGH', 'Saturacion de oxigeno por debajo del 90%', 'OPEN');

COMMIT;