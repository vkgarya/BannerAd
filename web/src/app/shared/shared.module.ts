import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgxDatatableModule } from '@swimlane/ngx-datatable';
import { CdkStepperModule } from '@angular/cdk/stepper';
import { MatStepperModule } from '@angular/material/stepper';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatRadioModule } from '@angular/material/radio';
import { MatIconModule } from '@angular/material/icon';
import { NgbDropdownModule } from '@ng-bootstrap/ng-bootstrap';
import { ModalModule } from 'ngx-bootstrap/modal';

import { PageTitleComponent } from './components/page-title/page-title.component';
import { DataTableSearchComponent } from './components/data-table-search/data-table-search.component';
import { DataTableColumnFilterComponent } from './components/data-table-column-filter/data-table-column-filter.component';

const DECLARATIONS = [
  PageTitleComponent,
  DataTableSearchComponent,
  DataTableColumnFilterComponent
];
const IMPORTS = [
  FormsModule,
  ReactiveFormsModule,
  NgxDatatableModule,
  NgbDropdownModule,
  CdkStepperModule,
  MatStepperModule,
  MatButtonModule,
  MatInputModule,
  MatCheckboxModule,
  MatRadioModule,
  MatIconModule
];

@NgModule({
  declarations: [
    DECLARATIONS
  ],
  imports: [
    CommonModule,
    IMPORTS,
    ModalModule.forRoot()
  ],
  exports: [
    DECLARATIONS,
    IMPORTS,
    ModalModule
  ]
})
export class SharedModule { }
