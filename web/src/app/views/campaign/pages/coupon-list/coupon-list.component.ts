import { Component, OnInit, ViewChild, TemplateRef } from '@angular/core';

import { DatatableComponent } from '@swimlane/ngx-datatable';
import { CouponService } from 'src/app/core/services/coupon/coupon.service';



import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';

@Component({
  selector: 'app-coupon-list',
  templateUrl: './coupon-list.component.html',
  styleUrls: ['./coupon-list.component.scss']
})
export class CouponListComponent implements OnInit {
  
  action = 'list';
  couponsList: any;
  selectedCoupon: any;
  columns = [
    { prop: 'id', name: 'Coupon Id',flexSize: 1 },
    { prop: 'codes', name: 'Coupon Code',flexSize: 2 },
    { prop: 'coupon_type', name: 'Coupon Type',flexSize: 2 },
    { prop: 'started_on', name: 'Started On',flexSize: 2 },
    { prop: 'closed_on', name: 'Closed On',flexSize: 2 }
  ];
  pageTitleInfo = {
    title: 'Coupons',
    icon: 'assets/images/coupon.png'
  };

  @ViewChild(DatatableComponent, { static: false }) table: DatatableComponent;
  modalRef: BsModalRef;

  constructor(private readonly _couponService: CouponService, private readonly modalService: BsModalService) { }

  ngOnInit() {
    this.getCouponsList();
  }

  getCouponsList(): void {
    this._couponService.getCouponsList().subscribe(couponsList => {
      this.couponsList = couponsList;
      //console.log(couponsList);
    });
  }

  getCouponCodes(codes: any): string {
    let codeString = [];
    if (codes) {
      codes.forEach(code => {
        codeString.push(code.value);
      });
    }

    return codeString.join();
  }

  getDescriptions(descriptions: any): string {
    let descriptionString = [];
    descriptions.forEach(description => {
      descriptionString.push(description.value);
    });

    return descriptionString.join();
  }

  openCouponDetailModal(modalTemplate: TemplateRef<any>, coupon: any): void {
    this.modalRef = this.modalService.show(modalTemplate);
    this.selectedCoupon = coupon;
  }

  onEditCoupon(coupon: any): void {
    this.action = 'edit';
    this.pageTitleInfo.title = 'Coupons/Edit-coupon';
    this.selectedCoupon = coupon;
  }

  onDeleteCoupon(coupon: any): void {
    if (confirm('Are you sure want to delete?')) {
      this._couponService.deleteCoupon(coupon.id).subscribe(res => {
        alert("Coupon removed successfully!");
        this.getCouponsList();
      });
    }
  }

  backToList(): void { 
    this.pageTitleInfo.title = "Coupons";
    this.getCouponsList();
    this.action = 'list';
  }

}
