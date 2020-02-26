import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-discount-report',
  templateUrl: './discount-report.component.html',
  styleUrls: ['./discount-report.component.scss']
})
export class DiscountReportComponent implements OnInit {

  rows = [
    {
      date: '2020-02-28',
      discountPercentage: '20%',
      totalUsers: '1000',
      successfulUsers: '700'
    },
    {
      date: '2020-02-28',
      discountPercentage: '20%',
      totalUsers: '1000',
      successfulUsers: '700'
    },
    {
      date: '2020-02-28',
      discountPercentage: '20%',
      totalUsers: '1000',
      successfulUsers: '700'
    },
    {
      date: '2020-02-28',
      discountPercentage: '20%',
      totalUsers: '1000',
      successfulUsers: '700'
    },
    {
      date: '2020-02-28',
      discountPercentage: '20%',
      totalUsers: '1000',
      successfulUsers: '700'
    },
    {
      date: '2020-02-28',
      discountPercentage: '20%',
      totalUsers: '1000',
      successfulUsers: '700'
    },
    {
      date: '2020-02-28',
      discountPercentage: '20%',
      totalUsers: '1000',
      successfulUsers: '700'
    },
    {
      date: '2020-02-28',
      discountPercentage: '20%',
      totalUsers: '1000',
      successfulUsers: '700'
    },
    {
      date: '2020-02-28',
      discountPercentage: '20%',
      totalUsers: '1000',
      successfulUsers: '700'
    },
    {
      date: '2020-02-28',
      discountPercentage: '20%',
      totalUsers: '1000',
      successfulUsers: '700'
    },
    {
      date: '2020-02-28',
      discountPercentage: '20%',
      totalUsers: '1000',
      successfulUsers: '700'
    },

  
 
  ];
  temp = [];
  columns = [ 
    { prop: 'date', name: 'Date', flexSize: 1 },
    { prop: 'discountPercentage', name: 'Discount %', flexSize: 1 },
    { prop: 'totalUsers', name: 'Total Users', flexSize: 1 },
    { prop: 'successfulUsers', name: 'Successful Users', flexSize: 1 }
  ];
  pageTitleInfo = {
    title: 'Discounts Report',
    icon: 'assets/images/discount.png'
  };


  constructor() { }

  ngOnInit() {
  }

}
