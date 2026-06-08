import { Routes } from '@angular/router';
import { authGuard } from './core/auth/auth-guard';
import { Alerts } from './features/alerts/alerts';
import { Home } from './features/home/home';
import { Patients } from './features/patients/patients';
import { VitalSigns } from './features/vital-signs/vital-signs';

export const routes: Routes = [
  {
    path: '',
    component: Home,
  },
  {
    path: 'patients',
    component: Patients,
    canActivate: [authGuard],
  },
  {
    path: 'vital-signs',
    component: VitalSigns,
    canActivate: [authGuard],
  },
  {
    path: 'alerts',
    component: Alerts,
    canActivate: [authGuard],
  },
  {
    path: '**',
    redirectTo: '',
  },
];
