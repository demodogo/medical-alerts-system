import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { MsalService } from '@azure/msal-angular';
import { AccountInfo } from '@azure/msal-browser';
import { azureB2cConfig } from './core/auth/auth-config';
import { PatientsService } from './core/services/patients';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet, RouterLink, RouterLinkActive],
  templateUrl: './app.html',
  styleUrl: './app.css',
})
export class AppComponent implements OnInit {
  account: AccountInfo | null = null;
  patients: unknown[] = [];
  accessToken = '';
  errorMessage = '';
  isInitialized = false;

  constructor(
    private readonly msalService: MsalService,
    private readonly patientsService: PatientsService,
  ) {}

  ngOnInit(): void {
    this.msalService.instance.handleRedirectPromise().then((result) => {
      if (result?.account) {
        this.account = result.account;
        this.msalService.instance.setActiveAccount(result.account);
      } else {
        const accounts = this.msalService.instance.getAllAccounts();

        if (accounts.length > 0) {
          this.account = accounts[0];
          this.msalService.instance.setActiveAccount(this.account);
        }
      }

      if (result?.accessToken) {
        this.accessToken = result.accessToken;
        localStorage.setItem('medical_alerts_access_token', result.accessToken);
        this.isInitialized = true;
      } else if (this.account) {
        this.getToken();
      } else {
        this.isInitialized = true;
      }
    });
  }

  get userDisplayName(): string {
    if (!this.account) {
      return 'Not authenticated';
    }

    const claims = this.account.idTokenClaims as Record<string, unknown>;

    return (
      (claims['name'] as string) ||
      (claims['emails'] as string[] | undefined)?.[0] ||
      (claims['email'] as string) ||
      this.account.username ||
      this.account.localAccountId ||
      'Authenticated user'
    );
  }

  login(): void {
    this.msalService.loginRedirect({
      scopes: [azureB2cConfig.apiScope],
    });
  }

  logout(): void {
    localStorage.removeItem('medical_alerts_access_token');
    this.msalService.logoutRedirect();
  }

  requestApiToken(): void {
    this.msalService.acquireTokenRedirect({
      scopes: [azureB2cConfig.apiScope],
    });
  }

  loadPatients(): void {
    this.errorMessage = '';

    const storedToken = localStorage.getItem('medical_alerts_access_token');

    if (storedToken) {
      this.patientsService.findAll(storedToken).subscribe({
        next: (patients) => {
          this.patients = patients;
        },
        error: (error) => {
          this.errorMessage = `BFF request failed: ${error.status} ${error.statusText}`;
          console.error(error);
        },
      });

      return;
    }

    const account = this.msalService.instance.getActiveAccount();

    if (!account) {
      this.errorMessage = 'No active account found';
      return;
    }

    this.msalService
      .acquireTokenSilent({
        account,
        scopes: [azureB2cConfig.apiScope],
      })
      .subscribe({
        next: (result) => {
          if (!result.accessToken) {
            this.errorMessage = 'MSAL did not return an access token';
            return;
          }

          this.accessToken = result.accessToken;
          localStorage.setItem('medical_alerts_access_token', result.accessToken);

          this.patientsService.findAll(result.accessToken).subscribe({
            next: (patients) => {
              this.patients = patients;
            },
            error: (error) => {
              this.errorMessage = `BFF request failed: ${error.status} ${error.statusText}`;
              console.error(error);
            },
          });
        },
        error: (error) => {
          this.errorMessage = 'Could not acquire access token silently';
          console.error(error);
        },
      });
  }

  getToken(): void {
    const account = this.msalService.instance.getActiveAccount();

    if (!account) {
      this.errorMessage = 'No active account found';
      this.isInitialized = true;
      return;
    }

    this.msalService
      .acquireTokenSilent({
        account,
        scopes: [azureB2cConfig.apiScope],
      })
      .subscribe({
        next: (result) => {
          this.accessToken = result.accessToken;
          localStorage.setItem('medical_alerts_access_token', result.accessToken);
          this.isInitialized = true;
        },
        error: (error) => {
          this.errorMessage = 'Could not acquire access token';
          console.error(error);
          this.isInitialized = true;
        },
      });
  }
}
