import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { CampaignComponent } from './campaign.component';
import { BannerListComponent } from './pages/banner-list/banner-list.component';
import { VideoListComponent } from './pages/video-list/video-list.component';
import { CouponListComponent } from './pages/coupon-list/coupon-list.component';
import { ReferralListComponent } from './pages/referral-list/referral-list.component';
import { DiscountListComponent } from './pages/discount-list/discount-list.component';
import { LoyaltyPointListComponent } from './pages/loyalty-point-list/loyalty-point-list.component';
import { AddBannerComponent } from './pages/add-banner/add-banner.component';
import { AddVideoComponent } from './pages/add-video/add-video.component';
import { AddCouponComponent } from './pages/add-coupon/add-coupon.component';
import { AddDiscountComponent } from './pages/add-discount/add-discount.component';
import { AddReferralComponent } from './pages/add-referral/add-referral.component';
import { AddLoyaltyPointComponent } from './pages/add-loyalty-point/add-loyalty-point.component';
import { NewAddCouponComponent } from './pages/new-add-coupon/new-add-coupon.component';


const routes: Routes = [
  {
    path: '',
    component: CampaignComponent,
    children: [
      {
        path: 'banner-list',
        component: BannerListComponent
      },
      {
        path: 'add-banner',
        component: AddBannerComponent
      },
      {
        path: 'video-list',
        component: VideoListComponent
      },
      {
        path: 'add-video',
        component: AddVideoComponent
      },
      {
        path: 'coupon-list',
        component: CouponListComponent
      },
      {
        path: 'add-coupon',
        component: NewAddCouponComponent
      },
      {
        path: 'edit-coupon/:id',
        component: NewAddCouponComponent
      },
      {
        path: 'referral-list',
        component: ReferralListComponent
      },
      {
        path: 'add-referral',
        component: AddReferralComponent
      },
      {
        path: 'discount-list',
        component: DiscountListComponent
      },
      {
        path: 'add-discount',
        component: AddDiscountComponent
      },
      {
        path: 'loyalty-point-list',
        component: LoyaltyPointListComponent
      },
      {
        path: 'add-loyalty-point',
        component: AddLoyaltyPointComponent
      },
      {
        path: '',
        redirectTo: 'banner-list',
        pathMatch: 'full'
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CampaignRoutingModule { }
