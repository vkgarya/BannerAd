import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AdminComponent } from './admin.component';
import { CustomEventListComponent } from './pages/custom-event-list/custom-event-list.component';
import { ManageUserListComponent } from './pages/manage-user-list/manage-user-list.component';
import { ConfigurePropertyListComponent } from './pages/configure-property-list/configure-property-list.component';
import { UserEventsListComponent } from './pages/user-events-list/user-events-list.component';
import { AddCustomEventComponent } from './pages/add-custom-event/add-custom-event.component';
import { AddUserComponent } from './pages/add-user/add-user.component';
import { AddPropertyComponent } from './pages/add-property/add-property.component';
import { AddUserEventComponent } from './pages/add-user-event/add-user-event.component';


const routes: Routes = [
  {
    path: '',
    component: AdminComponent,
    children: [
      {
        path: 'custom-event-list',
        component: CustomEventListComponent
      },
      {
        path: 'add-custom-event',
        component: AddCustomEventComponent
      },
      {
        path: 'user-list',
        component: ManageUserListComponent
      },
      {
        path: 'add-user',
        component: AddUserComponent
      },
      {
        path: 'configure-property-list',
        component: ConfigurePropertyListComponent
      },
      {
        path: 'add-property',
        component: AddPropertyComponent
      },
      {
        path: 'user-event-list',
        component: UserEventsListComponent
      },
      {
        path: 'add-user-event',
        component: AddUserEventComponent
      },
      {
        path: '',
        redirectTo: 'custom-event-list',
        pathMatch: 'full'
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
