import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';

import { PatientsService } from '../../core/services/patients';
import { VitalSignsService } from '../../core/services/vital-signs';
import { Patient } from '../../shared/models/patient.model';
import { VitalSign, VitalSignRequest } from '../../shared/models/vital-sign.model';

@Component({
  selector: 'app-vital-signs',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './vital-signs.html',
  styleUrl: './vital-signs.css',
})
export class VitalSigns implements OnInit {
  private readonly formBuilder = inject(FormBuilder);
  private readonly vitalSignsService = inject(VitalSignsService);
  private readonly patientsService = inject(PatientsService);

  vitalSigns: VitalSign[] = [];
  patients: Patient[] = [];
  selectedVitalSignId: number | null = null;
  errorMessage = '';
  successMessage = '';

  readonly form = this.formBuilder.group({
    patientId: [1, [Validators.required, Validators.min(1)]],
    heartRate: [80, [Validators.required, Validators.min(1)]],
    oxygenSaturation: [98, [Validators.required, Validators.min(1), Validators.max(100)]],
    temperature: [36.5, [Validators.required, Validators.min(25), Validators.max(45)]],
    systolicPressure: [120, [Validators.required, Validators.min(1)]],
    diastolicPressure: [80, [Validators.required, Validators.min(1)]],
  });

  ngOnInit(): void {
    this.loadPatients();
    this.loadVitalSigns();
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

  loadVitalSigns(): void {
    const accessToken = this.getAccessToken();

    if (!accessToken) {
      this.errorMessage = 'No autorizado';
      return;
    }

    this.errorMessage = '';

    this.vitalSignsService.findAll(accessToken).subscribe({
      next: (vitalSigns) => {
        this.vitalSigns = vitalSigns;
      },
      error: (error) => {
        this.errorMessage = `Error al cargar registros de  signos vitales: ${error.status} ${error.statusText}`;
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

    const request: VitalSignRequest = {
      patientId: Number(rawValue.patientId),
      heartRate: Number(rawValue.heartRate),
      oxygenSaturation: Number(rawValue.oxygenSaturation),
      temperature: Number(rawValue.temperature),
      systolicPressure: Number(rawValue.systolicPressure),
      diastolicPressure: Number(rawValue.diastolicPressure),
    };

    if (this.selectedVitalSignId) {
      this.updateVitalSign(this.selectedVitalSignId, request, accessToken);
      return;
    }

    this.createVitalSign(request, accessToken);
  }

  editVitalSign(vitalSign: VitalSign): void {
    this.selectedVitalSignId = vitalSign.id;

    this.form.patchValue({
      patientId: vitalSign.patientId,
      heartRate: vitalSign.heartRate,
      oxygenSaturation: vitalSign.oxygenSaturation,
      temperature: vitalSign.temperature,
      systolicPressure: vitalSign.systolicPressure,
      diastolicPressure: vitalSign.diastolicPressure,
    });
  }

  deleteVitalSign(id: number): void {
    const accessToken = this.getAccessToken();

    if (!accessToken) {
      this.errorMessage = 'No autorizado.';
      return;
    }

    this.vitalSignsService.delete(id, accessToken).subscribe({
      next: () => {
        this.successMessage = 'Registro borrado exitosamente';
        this.loadVitalSigns();
      },
      error: (error) => {
        this.errorMessage = `Error al eliminar registro: ${error.status} ${error.statusText}`;
        console.error(error);
      },
    });
  }

  cancelEdit(): void {
    this.selectedVitalSignId = null;

    this.form.reset({
      patientId: 1,
      heartRate: 80,
      oxygenSaturation: 98,
      temperature: 36.5,
      systolicPressure: 120,
      diastolicPressure: 80,
    });
  }

  private createVitalSign(request: VitalSignRequest, accessToken: string): void {
    this.vitalSignsService.create(request, accessToken).subscribe({
      next: () => {
        this.successMessage = 'Registro creado exitosamente.';
        this.cancelEdit();
        this.loadVitalSigns();
      },
      error: (error) => {
        this.errorMessage = `Error al crear registro: ${error.status} ${error.statusText}`;
        console.error(error);
      },
    });
  }

  private updateVitalSign(id: number, request: VitalSignRequest, accessToken: string): void {
    this.vitalSignsService.update(id, request, accessToken).subscribe({
      next: () => {
        this.successMessage = 'Registro actualizado exitosamente.';
        this.cancelEdit();
        this.loadVitalSigns();
      },
      error: (error) => {
        this.errorMessage = `Error al actualizar registro: ${error.status} ${error.statusText}`;
        console.error(error);
      },
    });
  }

  private getAccessToken(): string {
    return localStorage.getItem('medical_alerts_access_token') ?? '';
  }
}
