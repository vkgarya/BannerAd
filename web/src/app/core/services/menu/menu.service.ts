import { Injectable } from '@angular/core';

declare function require(url: string);

@Injectable({
  providedIn: 'root'
})
export class MenuService {

  constructor() { }

  getMenuItems(): any {
    return require('../../../../assets/data/menu2.json');
  }
}
