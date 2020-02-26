import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormArray, FormControl } from '@angular/forms';

@Component({
  selector: 'app-loyalty-point-report',
  templateUrl: './loyalty-point-report.component.html',
  styleUrls: ['./loyalty-point-report.component.scss']
})
export class LoyaltyPointReportComponent implements OnInit {

  rows = [
    {
      date: '2020-02-28',
      noOfUsers: '20%',
      totalLoyaltyPointsCredited: '1000'     
    },
    {
      date: '2020-02-28',
      noOfUsers: '20%',
      totalLoyaltyPointsCredited: '1000'     
    },
    {
      date: '2020-02-28',
      noOfUsers: '20%',
      totalLoyaltyPointsCredited: '1000'     
    },
    {
      date: '2020-02-28',
      noOfUsers: '20%',
      totalLoyaltyPointsCredited: '1000'     
    },
    {
      date: '2020-02-28',
      noOfUsers: '20%',
      totalLoyaltyPointsCredited: '1000'     
    },
    {
      date: '2020-02-28',
      noOfUsers: '20%',
      totalLoyaltyPointsCredited: '1000'     
    },
    {
      date: '2020-02-28',
      noOfUsers: '20%',
      totalLoyaltyPointsCredited: '1000'     
    },
    {
      date: '2020-02-28',
      noOfUsers: '20%',
      totalLoyaltyPointsCredited: '1000'     
    },
    {
      date: '2020-02-28',
      noOfUsers: '20%',
      totalLoyaltyPointsCredited: '1000'     
    },
    {
      date: '2020-02-28',
      noOfUsers: '20%',
      totalLoyaltyPointsCredited: '1000'     
    },
    {
      date: '2020-02-28',
      noOfUsers: '20%',
      totalLoyaltyPointsCredited: '1000'     
    },
   

  
 
  ];
  temp = [];
  columns = [ 
    { prop: 'date', name: 'Date', flexSize: 1 },
    { prop: 'noOfUsers', name: 'No. of Users', flexSize: 1 },
    { prop: 'totalLoyaltyPointsCredited', name: 'Total Loyalty Points Credited in the Day', flexSize: 1 },

  ];
  pageTitleInfo = {
    title: 'Loyalty Points Report',
    icon: 'assets/images/loyalty_points.png'
  };

  constructor() { }

  ngOnInit() {
  }

}
