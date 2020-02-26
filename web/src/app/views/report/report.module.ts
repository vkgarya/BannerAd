import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';

import { ReportRoutingModule } from './report-routing.module';
import { SharedModule } from 'src/app/shared/shared.module';

import { ReportComponent } from './report.component';
import { CouponReportComponent } from './pages/coupon-report/coupon-report.component';
import { DiscountReportComponent } from './pages/discount-report/discount-report.component';
import { ReferralReportComponent } from './pages/referral-report/referral-report.component';
import { LoyaltyPointReportComponent } from './pages/loyalty-point-report/loyalty-point-report.component';

@NgModule({
  declarations: [ReportComponent, CouponReportComponent, DiscountReportComponent, ReferralReportComponent, LoyaltyPointReportComponent],
  imports: [
    CommonModule,
    ReportRoutingModule,
    SharedModule,
    BsDatepickerModule
  ]
})
export class ReportModule { }
