import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-configure-property-list',
  templateUrl: './configure-property-list.component.html',
  styleUrls: ['./configure-property-list.component.scss']
})
export class ConfigurePropertyListComponent implements OnInit {

  rows = [
    {
      id: '1',
      propertyName: 'House',
      propertyValue: '100000'
    },
    {
      id: '1',
      propertyName: 'House',
      propertyValue: '100000'
    },
    {
      id: '1',
      propertyName: 'House',
      propertyValue: '100000'
    },
    {
      id: '1',
      propertyName: 'House',
      propertyValue: '100000'
    },
    {
      id: '1',
      propertyName: 'House',
      propertyValue: '100000'
    },
    {
      id: '1',
      propertyName: 'House',
      propertyValue: '100000'
    },
    {
      id: '1',
      propertyName: 'House',
      propertyValue: '100000'
    },
    {
      id: '1',
      propertyName: 'House',
      propertyValue: '100000'
    },
    {
      id: '1',
      propertyName: 'House',
      propertyValue: '100000'
    },
    {
      id: '1',
      propertyName: 'House',
      propertyValue: '100000'
    },
    {
      id: '1',
      propertyName: 'House',
      propertyValue: '100000'
    },
    {
      id: '1',
      propertyName: 'House',
      propertyValue: '100000'
    },

  ];
  temp = [];
  columns = [
    { prop: 'id', name: 'Id', flexSize: 1 },
    { prop: 'propertyName', name: 'Property Name', flexSize: 1 },
    { prop: 'propertyValue', name: 'Property Value', flexSize: 1 },


  ];
  pageTitleInfo = {
    title: 'Properties',
    icon: 'assets/images/property.png'
  };

  constructor() { }

  ngOnInit() {
  }

}
