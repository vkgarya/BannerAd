import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ReportComponent } from './report.component';
import { CouponReportComponent } from './pages/coupon-report/coupon-report.component';
import { ReferralReportComponent } from './pages/referral-report/referral-report.component';
import { DiscountReportComponent } from './pages/discount-report/discount-report.component';
import { LoyaltyPointReportComponent } from './pages/loyalty-point-report/loyalty-point-report.component';


const routes: Routes = [
  {
    path: '',
    component: ReportComponent,
    children: [
      {
        path: 'coupon-report',
        component: CouponReportComponent
      },
      {
        path: 'referral-report',
        component: ReferralReportComponent
      },
      {
        path: 'discount-report',
        component: DiscountReportComponent
      },
      {
        path: 'loyalty-point-report',
        component: LoyaltyPointReportComponent
      },
      {
        path: '',
        redirectTo: 'coupon-report',
        pathMatch: 'full'
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ReportRoutingModule { }
