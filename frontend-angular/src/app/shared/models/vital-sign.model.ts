export interface VitalSign {
  id: number;
  patientId: number;
  heartRate: number;
  oxygenSaturation: number;
  temperature: number;
  systolicPressure: number;
  diastolicPressure: number;
  measuredAt: string;
}

export interface VitalSignRequest {
  patientId: number;
  heartRate: number;
  oxygenSaturation: number;
  temperature: number;
  systolicPressure: number;
  diastolicPressure: number;
}
