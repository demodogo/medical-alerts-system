import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';

import { PatientsService } from '../../core/services/patients';
import { Patient, PatientRequest, PatientStatus } from '../../shared/models/patient.model';

@Component({
  selector: 'app-patients',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './patients.html',
  styleUrl: './patients.css',
})
export class Patients implements OnInit {
  private readonly formBuilder = inject(FormBuilder);
  private readonly patientsService = inject(PatientsService);

  patients: Patient[] = [];
  selectedPatientId: number | null = null;
  errorMessage = '';
  successMessage = '';

  readonly statuses: PatientStatus[] = ['CRITICAL', 'STABLE', 'DISCHARGED'];

  readonly form = this.formBuilder.group({
    fullName: ['', [Validators.required, Validators.maxLength(120)]],
    clinicalIdentifier: ['', [Validators.required, Validators.maxLength(50)]],
    room: ['', [Validators.required, Validators.maxLength(20)]],
    status: ['CRITICAL' as PatientStatus, [Validators.required]],
  });

  ngOnInit(): void {
    this.loadPatients();
  }

  loadPatients(): void {
    const accessToken = this.getAccessToken();

    if (!accessToken) {
      this.errorMessage = 'No autorizado';
      return;
    }

    this.errorMessage = '';

    this.patientsService.findAll(accessToken).subscribe({
      next: (patients: Patient[]) => {
        this.patients = patients;
      },
      error: (error: any) => {
        this.errorMessage = `Error al cargar los pacientes: ${error.status} ${error.statusText}`;
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

    const request = this.form.getRawValue() as PatientRequest;

    if (this.selectedPatientId) {
      this.updatePatient(this.selectedPatientId, request, accessToken);
      return;
    }

    this.createPatient(request, accessToken);
  }

  editPatient(patient: Patient): void {
    this.selectedPatientId = patient.id;

    this.form.patchValue({
      fullName: patient.fullName,
      clinicalIdentifier: patient.clinicalIdentifier,
      room: patient.room,
      status: patient.status,
    });
  }

  deletePatient(id: number): void {
    const accessToken = this.getAccessToken();

    if (!accessToken) {
      this.errorMessage = 'No autorizado.';
      return;
    }

    this.patientsService.delete(id, accessToken).subscribe({
      next: () => {
        this.successMessage = 'Paciente eliminado exitosamente.';
        this.loadPatients();
      },
      error: (error: any) => {
        this.errorMessage = `Error al eliminar el paciente: ${error.status} ${error.statusText}`;
        console.error(error);
      },
    });
  }

  cancelEdit(): void {
    this.selectedPatientId = null;
    this.form.reset({
      fullName: '',
      clinicalIdentifier: '',
      room: '',
      status: 'CRITICAL',
    });
  }

  private createPatient(request: PatientRequest, accessToken: string): void {
    this.patientsService.create(request, accessToken).subscribe({
      next: () => {
        this.successMessage = 'Paciente creado exitosamente';
        this.cancelEdit();
        this.loadPatients();
      },
      error: (error) => {
        this.errorMessage = `Error al crear paciente: ${error.status} ${error.statusText}`;
        console.error(error);
      },
    });
  }

  private updatePatient(id: number, request: PatientRequest, accessToken: string): void {
    this.patientsService.update(id, request, accessToken).subscribe({
      next: () => {
        this.successMessage = 'Paciente actualizado exitosamente.';
        this.cancelEdit();
        this.loadPatients();
      },
      error: (error: any) => {
        this.errorMessage = `Error al actualziar paciente: ${error.status} ${error.statusText}`;
        console.error(error);
      },
    });
  }

  private getAccessToken(): string {
    return localStorage.getItem('medical_alerts_access_token') ?? '';
  }
}
