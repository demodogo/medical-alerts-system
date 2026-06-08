import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { azureB2cConfig } from '../auth/auth-config';

@Injectable({
  providedIn: 'root',
})
export class PatientsService {
  private readonly apiUrl = `${azureB2cConfig.apiBaseUrl}/patients`;

  constructor(private readonly http: HttpClient) {}

  findAllWithToken(accessToken: string) {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${accessToken}`,
    });

    return this.http.get<unknown[]>(this.apiUrl, { headers });
  }
}
