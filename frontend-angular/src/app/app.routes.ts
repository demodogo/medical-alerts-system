import { Routes } from '@angular/router';
import { Alerts } from './features/alerts/alerts';
import { Patients } from './features/patients/patients';
import { VitalSigns } from './features/vital-signs/vital-signs';

export const routes: Routes = [
  {
    path: '',
    redirectTo: 'patients',
    pathMatch: 'full',
  },
  {
    path: 'patients',
    component: Patients,
  },
  {
    path: 'vital-signs',
    component: VitalSigns,
  },
  {
    path: 'alerts',
    component: Alerts,
  },
];
