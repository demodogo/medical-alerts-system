import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';

import { AlertsService } from '../../core/services/alerts';
import { PatientsService } from '../../core/services/patients';
import {
  AlertSeverity,
  AlertStatus,
  MedicalAlert,
  MedicalAlertRequest,
} from '../../shared/models/alert.model';
import { Patient } from '../../shared/models/patient.model';

@Component({
  selector: 'app-alerts',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './alerts.html',
  styleUrl: './alerts.css',
})
export class Alerts implements OnInit {
  private readonly formBuilder = inject(FormBuilder);
  private readonly alertsService = inject(AlertsService);
  private readonly patientsService = inject(PatientsService);

  alerts: MedicalAlert[] = [];
  patients: Patient[] = [];
  selectedAlertId: number | null = null;
  errorMessage = '';
  successMessage = '';

  readonly severities: AlertSeverity[] = ['LOW', 'MEDIUM', 'HIGH', 'CRITICAL'];
  readonly statuses: AlertStatus[] = ['OPEN', 'ACKNOWLEDGED', 'CLOSED'];

  readonly alertTypeTranslations: Record<string, string> = {
    LOW_OXYGEN: 'Oxígeno Bajo',
    LOW_SYSTOLIC: 'Presión Sistólica Baja',
    HIGH_HEART_RATE: 'Frecuencia Cardíaca Alta',
    LOW_HEART_RATE: 'Frecuencia Cardíaca Baja',
    HIGH_TEMPERATURE: 'Temperatura Alta',
    LOW_TEMPERATURE: 'Temperatura Baja',
  };

  readonly alertTypes = [
    'LOW_OXYGEN',
    'LOW_SYSTOLIC',
    'HIGH_HEART_RATE',
    'LOW_HEART_RATE',
    'HIGH_TEMPERATURE',
    'LOW_TEMPERATURE',
  ];

  readonly severityTranslations: Record<AlertSeverity, string> = {
    LOW: 'Baja',
    MEDIUM: 'Media',
    HIGH: 'Alta',
    CRITICAL: 'Crítica',
  };

  readonly statusTranslations: Record<AlertStatus, string> = {
    OPEN: 'Abierta',
    ACKNOWLEDGED: 'Recibido',
    CLOSED: 'Cerrada',
  };

  readonly form = this.formBuilder.group({
    patientId: [1, [Validators.required, Validators.min(1)]],
    alertType: ['LOW_OXYGEN', [Validators.required, Validators.maxLength(50)]],
    severity: ['HIGH' as AlertSeverity, [Validators.required]],
    message: [
      'Saturación de oxígeno por debajo del umbral mínimo',
      [Validators.required, Validators.maxLength(255)],
    ],
    status: ['OPEN' as AlertStatus, [Validators.required]],
  });

  ngOnInit(): void {
    this.loadPatients();
    this.loadAlerts();
  }

  loadPatients(): void {
    const accessToken = this.getAccessToken();

    if (!accessToken) {
      return;
    }

    this.patientsService.findAll(accessToken).subscribe({
      next: (patients) => {
        this.patients = patients;
      },
      error: (error) => {
        console.error(error);
      },
    });
  }

  getPatientName(patientId: number): string {
    const patient = this.patients.find((p) => p.id === patientId);
    return patient ? patient.fullName : `Paciente ${patientId}`;
  }

  loadAlerts(): void {
    const accessToken = this.getAccessToken();

    if (!accessToken) {
      this.errorMessage = 'No autorizado.';
      return;
    }

    this.errorMessage = '';

    this.alertsService.findAll(accessToken).subscribe({
      next: (alerts) => {
        this.alerts = alerts;
      },
      error: (error) => {
        this.errorMessage = `Error al cargar alertas: ${error.status} ${error.statusText}`;
        console.error(error);
      },
    });
  }

  submit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const accessToken = this.getAccessToken();

    if (!accessToken) {
      this.errorMessage = 'No autorizado.';
      return;
    }

    const rawValue = this.form.getRawValue();

    const request: MedicalAlertRequest = {
      patientId: Number(rawValue.patientId),
      alertType: String(rawValue.alertType),
      severity: rawValue.severity as AlertSeverity,
      message: String(rawValue.message),
      status: rawValue.status as AlertStatus,
    };

    if (this.selectedAlertId) {
      this.updateAlert(this.selectedAlertId, request, accessToken);
      return;
    }

    this.createAlert(request, accessToken);
  }

  editAlert(alert: MedicalAlert): void {
    this.selectedAlertId = alert.id;

    this.form.patchValue({
      patientId: alert.patientId,
      alertType: alert.alertType,
      severity: alert.severity,
      message: alert.message,
      status: alert.status,
    });
  }

  deleteAlert(id: number): void {
    const accessToken = this.getAccessToken();

    if (!accessToken) {
      this.errorMessage = 'No autorizado.';
      return;
    }

    this.alertsService.delete(id, accessToken).subscribe({
      next: () => {
        this.successMessage = 'Alerta eliminada exitosamente';
        this.loadAlerts();
      },
      error: (error) => {
        this.errorMessage = `Error al eliminar alerta: ${error.status} ${error.statusText}`;
        console.error(error);
      },
    });
  }

  cancelEdit(): void {
    this.selectedAlertId = null;

    this.form.reset({
      patientId: 1,
      alertType: 'LOW_OXYGEN',
      severity: 'HIGH',
      message: 'Saturación de oxígeno por debajo del umbral mínimo',
      status: 'OPEN',
    });
  }

  private createAlert(request: MedicalAlertRequest, accessToken: string): void {
    this.alertsService.create(request, accessToken).subscribe({
      next: () => {
        this.successMessage = 'Alerta creada exitosamente.';
        this.cancelEdit();
        this.loadAlerts();
      },
      error: (error) => {
        this.errorMessage = `Error al crear alerta: ${error.status} ${error.statusText}`;
        console.error(error);
      },
    });
  }

  private updateAlert(id: number, request: MedicalAlertRequest, accessToken: string): void {
    this.alertsService.update(id, request, accessToken).subscribe({
      next: () => {
        this.successMessage = 'Alerta actualizada exitosamente.';
        this.cancelEdit();
        this.loadAlerts();
      },
      error: (error) => {
        this.errorMessage = `Error al actualizar alerta: ${error.status} ${error.statusText}`;
        console.error(error);
      },
    });
  }

  private getAccessToken(): string {
    return localStorage.getItem('medical_alerts_access_token') ?? '';
  }
}
