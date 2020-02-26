import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { NgSelectModule } from '@ng-select/ng-select';

import { SharedModule } from 'src/app/shared/shared.module';

import { CampaignRoutingModule } from './campaign-routing.module';
import { CampaignComponent } from './campaign.component';
import { BannerListComponent } from './pages/banner-list/banner-list.component';
import { AddBannerComponent } from './pages/add-banner/add-banner.component';
import { VideoListComponent } from './pages/video-list/video-list.component';
import { AddVideoComponent } from './pages/add-video/add-video.component';
import { CouponListComponent } from './pages/coupon-list/coupon-list.component';
import { AddCouponComponent } from './pages/add-coupon/add-coupon.component';
import { ReferralListComponent } from './pages/referral-list/referral-list.component';
import { AddReferralComponent } from './pages/add-referral/add-referral.component';
import { DiscountListComponent } from './pages/discount-list/discount-list.component';
import { AddDiscountComponent } from './pages/add-discount/add-discount.component';
import { LoyaltyPointListComponent } from './pages/loyalty-point-list/loyalty-point-list.component';
import { AddLoyaltyPointComponent } from './pages/add-loyalty-point/add-loyalty-point.component';
import { NewAddCouponComponent } from './pages/new-add-coupon/new-add-coupon.component';
import { EditBannerComponent } from './pages/edit-banner/edit-banner.component';
import { NewEditCouponComponent } from './pages/new-edit-coupon/new-edit-coupon.component';

@NgModule({
  declarations: [CampaignComponent, BannerListComponent, AddBannerComponent, VideoListComponent, AddVideoComponent, CouponListComponent, AddCouponComponent, ReferralListComponent, AddReferralComponent, DiscountListComponent, AddDiscountComponent, LoyaltyPointListComponent, AddLoyaltyPointComponent, NewAddCouponComponent, EditBannerComponent, NewEditCouponComponent],
  imports: [
    CommonModule,
    CampaignRoutingModule,
    BsDatepickerModule.forRoot(),
    SharedModule,
    NgSelectModule
  ]
})
export class CampaignModule { }
