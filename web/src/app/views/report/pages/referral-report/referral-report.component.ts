import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-referral-report',
  templateUrl: './referral-report.component.html',
  styleUrls: ['./referral-report.component.scss']
})
export class ReferralReportComponent implements OnInit {


  rows = [
    {
      date: '2020-02-28',
      refererId: '2',
      totalUsers: '1000',
      noOfSuccessfulReferrals: '700'
    },
    {
      date: '2020-02-28',
      refererId: '2',
      totalUsers: '1000',
      noOfSuccessfulReferrals: '700'
    },
    {
      date: '2020-02-28',
      refererId: '2',
      totalUsers: '1000',
      noOfSuccessfulReferrals: '700'
    },
    {
      date: '2020-02-28',
      refererId: '2',
      totalUsers: '1000',
      noOfSuccessfulReferrals: '700'
    },
    {
      date: '2020-02-28',
      refererId: '2',
      totalUsers: '1000',
      noOfSuccessfulReferrals: '700'
    },
    {
      date: '2020-02-28',
      refererId: '2',
      totalUsers: '1000',
      noOfSuccessfulReferrals: '700'
    },
    {
      date: '2020-02-28',
      refererId: '2',
      totalUsers: '1000',
      noOfSuccessfulReferrals: '700'
    },
    {
      date: '2020-02-28',
      refererId: '2',
      totalUsers: '1000',
      noOfSuccessfulReferrals: '700'
    },
    {
      date: '2020-02-28',
      refererId: '2',
      totalUsers: '1000',
      noOfSuccessfulReferrals: '700'
    },
    {
      date: '2020-02-28',
      refererId: '2',
      totalUsers: '1000',
      noOfSuccessfulReferrals: '700'
    },
    {
      date: '2020-02-28',
      refererId: '2',
      totalUsers: '1000',
      noOfSuccessfulReferrals: '700'
    },
 

  
 
  ];
  temp = [];
  columns = [ 
    { prop: 'date', name: 'Date', flexSize: 1 },
    { prop: 'refererId', name: 'Referer Id', flexSize: 1 },
    { prop: 'noOfSuccessfulReferrals', name: 'No. Of Successful Referrals', flexSize: 1 },
   
  ];
  pageTitleInfo = {
    title: 'Referral Report',
    icon: 'assets/images/referrals.png'
  };

  constructor() { }

  ngOnInit() {
  }

}
