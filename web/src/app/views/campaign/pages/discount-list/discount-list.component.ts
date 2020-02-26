import { Component, ViewChild, OnInit } from '@angular/core';
import { DatatableComponent } from '@swimlane/ngx-datatable';

@Component({
  selector: 'app-discount-list',
  templateUrl: './discount-list.component.html',
  styleUrls: ['./discount-list.component.scss']
})
export class DiscountListComponent implements OnInit {

  rows = [
  
    {
      discountId: '01',
      discountDescription: 'Transaction 01',
      discountPercentage: '10',
      startedOn: '2020-02-28',
      closedOn: '2020-01-04'
    },
    {
      discountId: '02',
      discountDescription: 'Transaction 02',
      discountPercentage: '13',
      startedOn: '2020-02-28',
      closedOn: '2020-01-04'
    },
    {
      discountId: '03',
      discountDescription: 'Transaction 03',
      discountPercentage: '14',
      startedOn: '2020-02-28',
      closedOn: '2020-01-04'
    },
    {
      discountId: '04',
      discountDescription: 'Transaction 04',
      discountPercentage: '15',
      startedOn: '2020-02-28',
      closedOn: '2020-01-04'
    },
    {
      discountId: '05',
      discountDescription: 'Transaction 05',
      discountPercentage: '16',
      startedOn: '2020-02-28',
      closedOn: '2020-01-04'
    },
    {
      discountId: '06',
      discountDescription: 'Transaction 06',
      discountPercentage: '20',
      startedOn: '2020-02-28',
      closedOn: '2020-01-04'
    },
    {
      discountId: '07',
      discountDescription: 'Transaction 07',
      discountPercentage: '25',
      startedOn: '2020-02-28',
      closedOn: '2020-01-04'
    },
    {
      discountId: '08',
      discountDescription: 'Transaction 08',
      discountPercentage: '50',
      startedOn: '2020-02-28',
      closedOn: '2020-01-04'
    },
    {
      discountId: '09',
      discountDescription: 'Transaction 09',
      discountPercentage: '33',
      startedOn: '2020-02-28',
      closedOn: '2020-01-04'
    },
    {
      discountId: '10',
      discountDescription: 'Transaction 10',
      discountPercentage: '40',
      startedOn: '2020-02-28',
      closedOn: '2020-01-04'
    },
  
    {
      discountId: '08',
      discountDescription: 'Transaction 08',
      discountPercentage: '50',
      startedOn: '2020-02-28',
      closedOn: '2020-01-04'
    },
    {
      discountId: '09',
      discountDescription: 'Transaction 09',
      discountPercentage: '33',
      startedOn: '2020-02-28',
      closedOn: '2020-01-04'
    },
    {
      discountId: '10',
      discountDescription: 'Transaction 10',
      discountPercentage: '40',
      startedOn: '2020-02-28',
      closedOn: '2020-01-04'
    },
  

  ];
  temp = [];
  columns = [
    { prop: 'discountId', name: 'Discount Id' },
    { prop: 'discountDescription', name: 'Discount Description'},
    { prop: 'discountPercentage', name: 'Discount %' },
    { prop: 'startedOn', name: 'Started On' },
    { prop: 'closedOn', name: 'Closed On' }
  ];

  pageTitleInfo = {
    title: 'Discounts',
    icon: 'assets/images/discount.png'
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
