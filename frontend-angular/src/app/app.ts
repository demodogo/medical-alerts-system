import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { MsalService } from '@azure/msal-angular';
import { AccountInfo } from '@azure/msal-browser';
import { azureB2cConfig } from './core/auth/auth-config';
import { PatientsService } from './core/services/patients';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './app.html',
  styleUrl: './app.css',
})
export class AppComponent implements OnInit {
  account: AccountInfo | null = null;
  patients: unknown[] = [];
  accessToken = '';
  errorMessage = '';

  constructor(
    private readonly msalService: MsalService,
    private readonly patientsService: PatientsService,
  ) {}

  ngOnInit(): void {
    this.msalService.instance.handleRedirectPromise().then((result) => {
      console.log('MSAL REDIRECT RESULT:', result);

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
        console.log('REDIRECT ACCESS TOKEN LENGTH:', result.accessToken.length);

        this.accessToken = result.accessToken;
        localStorage.setItem('medical_alerts_access_token', result.accessToken);
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
    console.log('LOGIN SCOPE:', azureB2cConfig.apiScope);
    this.msalService.loginRedirect({
      scopes: [azureB2cConfig.apiScope],
    });
  }

  requestApiToken(): void {
    console.log('REQUESTING API TOKEN WITH SCOPE:', azureB2cConfig.apiScope);

    this.msalService.acquireTokenRedirect({
      scopes: [azureB2cConfig.apiScope],
    });
  }

  loadPatients(): void {
    this.errorMessage = '';

    const storedToken = localStorage.getItem('medical_alerts_access_token');

    if (storedToken) {
      console.log('USING STORED TOKEN LENGTH:', storedToken.length);

      this.patientsService.findAllWithToken(storedToken).subscribe({
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

    console.log('ACQUIRING TOKEN SILENT WITH SCOPE:', azureB2cConfig.apiScope);

    this.msalService
      .acquireTokenSilent({
        account,
        scopes: [azureB2cConfig.apiScope],
      })
      .subscribe({
        next: (result) => {
          console.log('MSAL TOKEN RESULT:', result);
          console.log('ACCESS TOKEN LENGTH:', result.accessToken?.length);

          if (!result.accessToken) {
            this.errorMessage = 'MSAL did not return an access token';
            return;
          }

          this.accessToken = result.accessToken;
          localStorage.setItem('medical_alerts_access_token', result.accessToken);

          this.patientsService.findAllWithToken(result.accessToken).subscribe({
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
        },
        error: (error) => {
          this.errorMessage = 'Could not acquire access token';
          console.error(error);
        },
      });
  }
}
