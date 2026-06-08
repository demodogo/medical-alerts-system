import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { MsalService } from '@azure/msal-angular';
import { azureB2cConfig } from '../../core/auth/auth-config';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './home.html',
  styleUrl: './home.css',
})
export class Home {
  constructor(private readonly msalService: MsalService) {}

  get isLoggedIn(): boolean {
    return (
      this.msalService.instance.getActiveAccount() !== null ||
      this.msalService.instance.getAllAccounts().length > 0
    );
  }

  get userDisplayName(): string {
    const account =
      this.msalService.instance.getActiveAccount() ||
      this.msalService.instance.getAllAccounts()[0];

    if (!account) {
      return '';
    }

    const claims = account.idTokenClaims as Record<string, unknown>;

    return (
      (claims['name'] as string) ||
      (claims['emails'] as string[] | undefined)?.[0] ||
      (claims['email'] as string) ||
      account.username ||
      account.localAccountId ||
      'Usuario Autenticado'
    );
  }

  login(): void {
    this.msalService.loginRedirect({
      scopes: [azureB2cConfig.apiScope],
    });
  }
}
