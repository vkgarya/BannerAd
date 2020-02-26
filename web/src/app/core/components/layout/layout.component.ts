import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-layout',
  templateUrl: './layout.component.html',
  styleUrls: ['./layout.component.scss']
})
export class LayoutComponent implements OnInit {

  sideNavCanvasType = 'side-nav-expanded';

  constructor() { }

  ngOnInit() {
  }

  onToggleSideNavCanvas(isSideNavMinimized: boolean): void {
    this.sideNavCanvasType = isSideNavMinimized ? 'side-nav-shrinked' : 'side-nav-expanded';
  }

}
