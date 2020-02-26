import { Component, OnInit, ViewChild } from '@angular/core';
import { DatatableComponent } from '@swimlane/ngx-datatable';

@Component({
  selector: 'app-referral-list',
  templateUrl: './referral-list.component.html',
  styleUrls: ['./referral-list.component.scss']
})
export class ReferralListComponent implements OnInit {

  rows = [
    {
      referId: '01',
      referralDescription: 'App Install',
      referrerPoints: '200',
      refereePoints: '100',
      startDate: '2020-02-28',
      endDate: '2020-01-04'
    },
    {
      referId: '02',
      referralDescription: 'App Install',
      referrerPoints: '200',
      refereePoints: '100',
      startDate: '2020-02-28',
      endDate: '2020-01-04'
    },
    {
      referId: '03',
      referralDescription: 'App Install',
      referrerPoints: '200',
      refereePoints: '100',
      startDate: '2020-02-28',
      endDate: '2020-01-04'
    },
    {
      referId: '04',
      referralDescription: 'App Install',
      referrerPoints: '200',
      refereePoints: '100',
      startDate: '2020-02-28',
      endDate: '2020-01-04'
    },
    {
      referId: '05',
      referralDescription: 'App Install',
      referrerPoints: '200',
      refereePoints: '100',
      startDate: '2020-02-28',
      endDate: '2020-01-04'
    },
    {
      referId: '06',
      referralDescription: 'App Install',
      referrerPoints: '200',
      refereePoints: '100',
      startDate: '2020-02-28',
      endDate: '2020-01-04'
    },
    {
      referId: '07',
      referralDescription: 'App Install',
      referrerPoints: '200',
      refereePoints: '100',
      startDate: '2020-02-28',
      endDate: '2020-01-04'
    },
    {
      referId: '08',
      referralDescription: 'App Install',
      referrerPoints: '200',
      refereePoints: '100',
      startDate: '2020-02-28',
      endDate: '2020-01-04'
    },
    {
      referId: '09',
      referralDescription: 'App Install',
      referrerPoints: '200',
      refereePoints: '100',
      startDate: '2020-02-28',
      endDate: '2020-01-04'
    },
    {
      referId: '10',
      referralDescription: 'App Install',
      referrerPoints: '200',
      refereePoints: '100',
      startDate: '2020-02-28',
      endDate: '2020-01-04'
    }
  

  ];
  temp = [];
  columns = [
    { prop: 'referId', name: 'Refer Id',flexSize: 1 },
    { prop: 'referralDescription', name: 'Referral Description',flexSize: 1},
    { prop: 'referrerPoints', name: 'Referrer Points',flexSize: 1 },
    { prop: 'refereePoints', name: 'Referee Points',flexSize: 1 },
    { prop: 'startDate', name: 'Start Date',flexSize: 1 },
    { prop: 'endDate', name: 'End Date',flexSize: 1 }
  ];
  pageTitleInfo = {
    title: 'Referrals',
    icon: 'assets/images/referrals.png'
  };

  @ViewChild(DatatableComponent, { static: false }) table: DatatableComponent;
 
  constructor() { }

  ngOnInit() {
  }

  
  fetch(cb) {
    const req = new XMLHttpRequest();
    req.open('GET', `assets/data/company.json`);

    req.onload = () => {
      cb(JSON.parse(req.response));
    };

    req.send();
  }

  updateFilter(event) {
    const val = event.target.value.toLowerCase();

    // filter our data
    const temp = this.temp.filter(function (d) {
      return d.name.toLowerCase().indexOf(val) !== -1 || !val;
    });

    // update the rows
    this.rows = temp;
    // Whenever the filter changes, always go back to the first page
    this.table.offset = 0;
  }

}
