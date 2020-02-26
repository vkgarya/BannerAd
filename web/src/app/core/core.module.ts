import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgbDropdownModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';

import { HttpClientModule } from '@angular/common/http';
import { RouterModule } from '@angular/router';

import { LayoutComponent } from './components/layout/layout.component';
import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';
import { SideNavComponent } from './components/side-nav/side-nav.component';

const DECLARATIONS = [
  LayoutComponent, HeaderComponent, FooterComponent, SideNavComponent
];

const IMPORTS = [
  HttpClientModule
];

@NgModule({
  declarations: [DECLARATIONS],
  imports: [
    CommonModule,
    RouterModule,
    IMPORTS,
    NgbDropdownModule,
    FormsModule
  ],
  exports: [
    IMPORTS,
    DECLARATIONS
  ]
})

export class CoreModule { }
