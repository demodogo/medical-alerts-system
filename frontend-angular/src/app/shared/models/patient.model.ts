export type PatientStatus = 'CRITICAL' | 'STABLE' | 'DISCHARGED';

export interface Patient {
  id: number;
  fullName: string;
  clinicalIdentifier: string;
  room: string;
  status: PatientStatus;
  createdAt: string;
}

export interface PatientRequest {
  fullName: string;
  clinicalIdentifier: string;
  room: string;
  status: PatientStatus;
}
