import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SharedModule } from 'src/app/shared/shared.module';
import { PartnersRoutingModule } from './partners-routing.module';

import { PartnersComponent } from './partners.component';
import { PartnersListComponent } from './pages/partners-list/partners-list.component';
import { AddPartnerComponent } from './pages/add-partner/add-partner.component';
import { EditPartnerComponent } from './pages/edit-partner/edit-partner.component';
import { BannerDemoComponent } from './pages/banner-demo/banner-demo.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

@NgModule({
  declarations: [PartnersComponent, PartnersListComponent, AddPartnerComponent, EditPartnerComponent, BannerDemoComponent],
  imports: [
    CommonModule,
    SharedModule,
    PartnersRoutingModule,
    NgbModule
  ]
})
export class PartnersModule { }
