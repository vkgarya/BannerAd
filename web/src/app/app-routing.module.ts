import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LayoutComponent } from './core/components/layout/layout.component';
import { AuthenticationGuard } from './guards/authentication/authentication.service';

const routes: Routes = [
  {
    path: '',
    component: LayoutComponent,
    children: [
      {
        path: 'partners',
        loadChildren: () => import('./views/partners/partners.module').then(module => module.PartnersModule),
        canLoad: [AuthenticationGuard]
      },
      {
        path: 'campaign',
        loadChildren: () => import('./views/campaign/campaign.module').then(module => module.CampaignModule),
        canLoad: [AuthenticationGuard]
      },
      {
        path: 'report',
        loadChildren: () => import('./views/report/report.module').then(module => module.ReportModule),
        canLoad: [AuthenticationGuard]
      },
      {
        path: 'admin',
        loadChildren: () => import('./views/admin/admin.module').then(module => module.AdminModule),
        canLoad: [AuthenticationGuard]
      },
      {
        path: '',
        redirectTo: 'partners',
        pathMatch: 'full'
      }
    ]
  },
  {
    path: '',
    loadChildren: () => import('./views/authentication/authentication.module').then(module => module.AuthenticationModule)
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
