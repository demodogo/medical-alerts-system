export type AlertSeverity = 'LOW' | 'MEDIUM' | 'HIGH' | 'CRITICAL';
export type AlertStatus = 'OPEN' | 'ACKNOWLEDGED' | 'CLOSED';

export interface MedicalAlert {
  id: number;
  patientId: number;
  alertType: string;
  severity: AlertSeverity;
  message: string;
  status: AlertStatus;
  createdAt: string;
}

export interface MedicalAlertRequest {
  patientId: number;
  alertType: string;
  severity: AlertSeverity;
  message: string;
  status: AlertStatus;
}
