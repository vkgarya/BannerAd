import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgSelectModule } from '@ng-select/ng-select';

import { SharedModule } from 'src/app/shared/shared.module';

import { AdminRoutingModule } from './admin-routing.module';
import { AdminComponent } from './admin.component';
import { CustomEventListComponent } from './pages/custom-event-list/custom-event-list.component';
import { AddCustomEventComponent } from './pages/add-custom-event/add-custom-event.component';
import { ManageUserListComponent } from './pages/manage-user-list/manage-user-list.component';
import { AddUserComponent } from './pages/add-user/add-user.component';
import { ConfigurePropertyListComponent } from './pages/configure-property-list/configure-property-list.component';
import { AddPropertyComponent } from './pages/add-property/add-property.component';
import { UserEventsListComponent } from './pages/user-events-list/user-events-list.component';
import { AddUserEventComponent } from './pages/add-user-event/add-user-event.component';
import { EditCustomEventComponent } from './pages/edit-custom-event/edit-custom-event/edit-custom-event.component';
import { EditUserComponent } from './pages/edit-user/edit-user.component';

@NgModule({
  declarations: [AdminComponent, CustomEventListComponent, AddCustomEventComponent, ManageUserListComponent, AddUserComponent, ConfigurePropertyListComponent, AddPropertyComponent, UserEventsListComponent, AddUserEventComponent, EditCustomEventComponent, EditUserComponent],
  imports: [
    CommonModule,
    AdminRoutingModule,
    SharedModule,
    NgSelectModule
  ]
})
export class AdminModule { }
