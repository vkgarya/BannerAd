import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-coupon-report',
  templateUrl: './coupon-report.component.html',
  styleUrls: ['./coupon-report.component.scss']
})
export class CouponReportComponent implements OnInit {

  rows = [
    {
      date: '2020-02-28',
      couponCode: 'MPAY50',
      totalUsers: '1000',
      successfulUsers: '700'
    },
    {
      date: '2020-02-28',
      couponCode: 'MPAY50',
      totalUsers: '1000',
      successfulUsers: '700'
    },
    {
      date: '2020-02-28',
      couponCode: 'MPAY50',
      totalUsers: '1000',
      successfulUsers: '700'
    },
    {
      date: '2020-02-28',
      couponCode: 'MPAY50',
      totalUsers: '1000',
      successfulUsers: '700'
    },
    {
      date: '2020-02-28',
      couponCode: 'MPAY50',
      totalUsers: '1000',
      successfulUsers: '700'
    },
    {
      date: '2020-02-28',
      couponCode: 'MPAY50',
      totalUsers: '1000',
      successfulUsers: '700'
    },
    {
      date: '2020-02-28',
      couponCode: 'MPAY50',
      totalUsers: '1000',
      successfulUsers: '700'
    },
    {
      date: '2020-02-28',
      couponCode: 'MPAY50',
      totalUsers: '1000',
      successfulUsers: '700'
    },
    {
      date: '2020-02-28',
      couponCode: 'MPAY50',
      totalUsers: '1000',
      successfulUsers: '700'
    },
    {
      date: '2020-02-28',
      couponCode: 'MPAY50',
      totalUsers: '1000',
      successfulUsers: '700'
    },
    {
      date: '2020-02-28',
      couponCode: 'MPAY50',
      totalUsers: '1000',
      successfulUsers: '700'
    },
  
 
  ];
  temp = [];
  columns = [ 
    { prop: 'date', name: 'Date', flexSize: 1 },
    { prop: 'couponCode', name: 'Coupon Code', flexSize: 1 },
    { prop: 'totalUsers', name: 'Total Users', flexSize: 1 },
    { prop: 'successfulUsers', name: 'Successful Users', flexSize: 2 }
  ];
  pageTitleInfo = {
    title: 'Coupons Report',
    icon: 'assets/images/coupon.png'
  };

  constructor() { }

  ngOnInit() {
  }

}
