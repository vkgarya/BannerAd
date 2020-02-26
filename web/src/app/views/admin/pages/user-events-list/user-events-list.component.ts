import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-user-events-list',
  templateUrl: './user-events-list.component.html',
  styleUrls: ['./user-events-list.component.scss']
})
export class UserEventsListComponent implements OnInit {
 

  rows = [
    {
      email: 'admin@vkandigital.com',
      partnerId: '0',
      role: 'admin',
      status: 'active',
    },
    {
      email: 'admin@vkandigital.com',
      partnerId: '0',
      role: 'admin',
      status: 'active',
    },
    {
      email: 'admin@vkandigital.com',
      partnerId: '0',
      role: 'admin',
      status: 'active',
    },
    {
      email: 'admin@vkandigital.com',
      partnerId: '0',
      role: 'admin',
      status: 'active',
    },
    {
      email: 'admin@vkandigital.com',
      partnerId: '0',
      role: 'admin',
      status: 'active',
    },
    {
      email: 'admin@vkandigital.com',
      partnerId: '0',
      role: 'admin',
      status: 'active',
    },
    {
      email: 'admin@vkandigital.com',
      partnerId: '0',
      role: 'admin',
      status: 'active',
    },
    {
      email: 'admin@vkandigital.com',
      partnerId: '0',
      role: 'admin',
      status: 'active',
    },
    {
      email: 'admin@vkandigital.com',
      partnerId: '0',
      role: 'admin',
      status: 'active',
    },
    {
      email: 'admin@vkandigital.com',
      partnerId: '0',
      role: 'admin',
      status: 'active',
    },
    {
      email: 'admin@vkandigital.com',
      partnerId: '0',
      role: 'admin',
      status: 'active',
    }


  ];
  temp = [];
  columns = [


    { prop: 'email', name: 'Email', flexSize: 1 },
    { prop: 'partnerId', name: 'Partner Id', flexSize: 1 },
    { prop: 'role', name: 'Role', flexSize: 1 },
    { prop: 'status', name: 'Status', flexSize: 1 },


  ];
  pageTitleInfo = {
    title: 'User Events',
    icon: 'assets/images/user_event.png'
  };


  constructor() { }

  ngOnInit() {
  }

}
