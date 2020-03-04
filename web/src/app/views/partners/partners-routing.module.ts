import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { PartnersComponent } from './partners.component';
import { PartnersListComponent } from './pages/partners-list/partners-list.component';
import { AddPartnerComponent } from './pages/add-partner/add-partner.component';
import { BannerDemoComponent } from './pages/banner-demo/banner-demo.component';

const routes: Routes = [
  {
    path: '',
    component: PartnersComponent,
    children: [
      {
        path: 'partners-list',
        component: PartnersListComponent
      },
      {
        path: 'add-partner',
        component: AddPartnerComponent
      },
      {
        path: 'banner-demo',
        component: BannerDemoComponent
      },
      {
        path: '',
        redirectTo: 'partners-list',
        pathMatch: 'full'
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PartnersRoutingModule { }
