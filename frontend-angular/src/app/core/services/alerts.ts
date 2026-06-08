import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { MedicalAlert, MedicalAlertRequest } from '../../shared/models/alert.model';
import { azureB2cConfig } from '../auth/auth-config';

@Injectable({
  providedIn: 'root',
})
export class AlertsService {
  private readonly apiUrl = `${azureB2cConfig.apiBaseUrl}/alerts`;

  constructor(private readonly http: HttpClient) {}

  findAll(accessToken: string) {
    return this.http.get<MedicalAlert[]>(this.apiUrl, {
      headers: this.createHeaders(accessToken),
    });
  }

  create(request: MedicalAlertRequest, accessToken: string) {
    return this.http.post<MedicalAlert>(this.apiUrl, request, {
      headers: this.createHeaders(accessToken),
    });
  }

  update(id: number, request: MedicalAlertRequest, accessToken: string) {
    return this.http.put<MedicalAlert>(`${this.apiUrl}/${id}`, request, {
      headers: this.createHeaders(accessToken),
    });
  }

  delete(id: number, accessToken: string) {
    return this.http.delete<void>(`${this.apiUrl}/${id}`, {
      headers: this.createHeaders(accessToken),
    });
  }

  private createHeaders(accessToken: string): HttpHeaders {
    return new HttpHeaders({
      Authorization: `Bearer ${accessToken}`,
    });
  }
}
