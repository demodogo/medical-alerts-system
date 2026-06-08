import { bootstrapApplication } from '@angular/platform-browser';
import 'zone.js';
import { AppComponent } from './app/app';
import { appConfig } from './app/app.config';
import { msalInstance } from './app/core/auth/auth-config';

msalInstance
  .initialize()
  .then(() => bootstrapApplication(AppComponent, appConfig))
  .catch((error) => console.error('Application bootstrap failed', error));
