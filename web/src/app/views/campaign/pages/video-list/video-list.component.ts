import { Component, OnInit, ViewChild } from '@angular/core';
import { DatatableComponent } from '@swimlane/ngx-datatable';
import { PartnersService } from 'src/app/core/services/partners/partners.service';


@Component({
  selector: 'app-video-list',
  templateUrl: './video-list.component.html',
  styleUrls: ['./video-list.component.scss']
})
export class VideoListComponent implements OnInit {


  rows = [
    {
      id: '01',
      video : 'video-01.mp4',
      clickType: 'inapp',
      balanceAmount: '0.00',
      balanceClicks: '900.00',
      from: '2020-01-04',
      to: '2020-02-28',
      status: 'Active'
    },
    {
      id: '02',
      video : 'video-02.mp4',
      clickType: 'native',
      balanceAmount: '0.00',
      balanceClicks: '900.00',
      from: '2020-01-04',
      to: '2020-02-28',
      status: 'Active'
    },
    {
      id: '03',
      video : 'video-03.mp4',
      clickType: 'external',
      balanceAmount: '0.00',
      balanceClicks: '900.00',
      from: '2020-01-04',
      to: '2020-02-28',
      status: 'Active'
    },
    {
      id: '04',
      video : 'video-04.mp4',
      clickType: 'inapp',
      balanceAmount: '0.00',
      balanceClicks: '900.00',
      from: '2020-01-04',
      to: '2020-02-28',
      status: 'Active'
    },
    {
      id: '05',
      video : 'video-05.mp4',
      clickType: 'native',
      balanceAmount: '0.00',
      balanceClicks: '900.00',
      from: '2020-01-04',
      to: '2020-02-28',
      status: 'Active'
    },
    {
      id: '06',
      video : 'video-06.mp4',
      clickType: 'external',
      balanceAmount: '0.00',
      balanceClicks: '900.00',
      from: '2020-01-04',
      to: '2020-02-28',
      status: 'Active'
    },
    {
      id: '07',
      video : 'video-07.mp4',
      clickType: 'inapp',
      balanceAmount: '0.00',
      balanceClicks: '900.00',
      from: '2020-01-04',
      to: '2020-02-28',
      status: 'Active'
    },
    {
      id: '08',
      video : 'video-08.mp4',
      clickType: 'native',
      balanceAmount: '0.00',
      balanceClicks: '900.00',
      from: '2020-01-04',
      to: '2020-02-28',
      status: 'Active'
    },
    {
      id: '09',
      video : 'video-09.mp4',
      clickType: 'external',
      balanceAmount: '0.00',
      balanceClicks: '900.00',
      from: '2020-01-04',
      to: '2020-02-28',
      status: 'Active'
    },
    {
      id: '10',
      video : 'video-10.mp4',
      clickType: 'inapp',
      balanceAmount: '0.00',
      balanceClicks: '900.00',
      from: '2020-01-04',
      to: '2020-02-28',
      status: 'Active'
    }
 
  ];
  temp = [];
  columns = [ 
    { prop: 'id', name: 'ID' },
    { prop: 'video', name: 'Video', flexSize: 1 },
    { prop: 'clickType', name: 'Click Type', flexSize: 1 },
    { prop: 'balanceAmount', name: 'Balance Amount(AED)', flexSize: 1 },
    { prop: 'balanceClicks', name: 'Balance Clicks', flexSize: 1 },
    { prop: 'from', name: 'From', flexSize: 1 },
    { prop: 'to', name: 'To', flexSize: 1 },
    { prop: 'status', name: 'Status', flexSize: 1 }
  ];
  pageTitleInfo = {
    title: 'Video Campaign',
    icon: 'assets/images/video_campaign.png'
  };
  
  @ViewChild(DatatableComponent, { static: false }) table: DatatableComponent;


  constructor(private readonly _partnerService: PartnersService) { }

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
    const temp = this.temp.filter(function(d) {
      return d.name.toLowerCase().indexOf(val) !== -1 || !val;
    });

    // update the rows
    this.rows = temp;
    // Whenever the filter changes, always go back to the first page
    this.table.offset = 0;
  }




}
