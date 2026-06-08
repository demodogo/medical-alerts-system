import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { VitalSign, VitalSignRequest } from '../../shared/models/vital-sign.model';
import { azureB2cConfig } from '../auth/auth-config';

@Injectable({
  providedIn: 'root',
})
export class VitalSignsService {
  private readonly apiUrl = `${azureB2cConfig.apiBaseUrl}/vital-signs`;

  constructor(private readonly http: HttpClient) {}

  findAll(accessToken: string) {
    return this.http.get<VitalSign[]>(this.apiUrl, {
      headers: this.createHeaders(accessToken),
    });
  }

  create(request: VitalSignRequest, accessToken: string) {
    return this.http.post<VitalSign>(this.apiUrl, request, {
      headers: this.createHeaders(accessToken),
    });
  }

  update(id: number, request: VitalSignRequest, accessToken: string) {
    return this.http.put<VitalSign>(`${this.apiUrl}/${id}`, request, {
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
