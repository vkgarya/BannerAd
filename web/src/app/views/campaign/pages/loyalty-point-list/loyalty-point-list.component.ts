import { Component, OnInit, ViewChild } from '@angular/core';
import { DatatableComponent } from '@swimlane/ngx-datatable';


@Component({
  selector: 'app-loyalty-point-list',
  templateUrl: './loyalty-point-list.component.html',
  styleUrls: ['./loyalty-point-list.component.scss']
})
export class LoyaltyPointListComponent implements OnInit {
  rows = [
    {
      loyaltyPointsId: '01',
      description: 'As a valued customer, giving 100 AED loyalty points',
      shortName: 'Purchased500AED',
      startDate: '2020-02-28',
      endDate: '2020-01-04'
    },
    {
      loyaltyPointsId: '02',
      description: 'As a valued customer, giving 100 AED loyalty points',
      shortName: 'Purchased500AED',
      startDate: '2020-02-28',
      endDate: '2020-01-04'
    },
    {
      loyaltyPointsId: '03',
      description: 'As a valued customer, giving 100 AED loyalty points',
      shortName: 'Purchased500AED',
      startDate: '2020-02-28',
      endDate: '2020-01-04'
    },
    {
      loyaltyPointsId: '04',
      description: 'As a valued customer, giving 100 AED loyalty points',
      shortName: 'Purchased500AED',
      startDate: '2020-02-28',
      endDate: '2020-01-04'
    },
    {
      loyaltyPointsId: '05',
      description: 'As a valued customer, giving 100 AED loyalty points',
      shortName: 'Purchased500AED',
      startDate: '2020-02-28',
      endDate: '2020-01-04'
    },
    {
      loyaltyPointsId: '06',
      description: 'As a valued customer, giving 100 AED loyalty points',
      shortName: 'Purchased500AED',
      startDate: '2020-02-28',
      endDate: '2020-01-04'
    },
    {
      loyaltyPointsId: '07',
      description: 'As a valued customer, giving 100 AED loyalty points',
      shortName: 'Purchased500AED',
      startDate: '2020-02-28',
      endDate: '2020-01-04'
    },
    {
      loyaltyPointsId: '08',
      description: 'As a valued customer, giving 100 AED loyalty points',
      shortName: 'Purchased500AED',
      startDate: '2020-02-28',
      endDate: '2020-01-04'
    },
    {
      loyaltyPointsId: '09',
      description: 'As a valued customer, giving 100 AED loyalty points',
      shortName: 'Purchased500AED',
      startDate: '2020-02-28',
      endDate: '2020-01-04'
    },
    {
      loyaltyPointsId: '10',
      description: 'As a valued customer, giving 100 AED loyalty points',
      shortName: 'Purchased500AED',
      startDate: '2020-02-28',
      endDate: '2020-01-04'
    },
  

  ];
  temp = [];
  columns = [
    { prop: 'loyaltyPointsId', name: 'Loyalty Points Id',flexSize: 1},
    { prop: 'description', name: 'Description',flexSize: 3},
    { prop: 'shortName', name: 'Short Name',flexSize: 2 },
    { prop: 'startDate', name: 'Start Date',flexSize: 1 },
    { prop: 'endDate', name: 'End Date',flexSize: 1 }
  ];
  pageTitleInfo = {
    title: 'Loyalty Points',
    icon: 'assets/images/loyalty_points.png'
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
