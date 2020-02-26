import { Component, OnInit, AfterViewInit, Output, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';
import * as $ from 'jquery';

import { MenuService } from '../../services/menu/menu.service';

@Component({
  selector: 'app-side-nav',
  templateUrl: './side-nav.component.html',
  styleUrls: ['./side-nav.component.scss']
})
export class SideNavComponent implements OnInit, AfterViewInit {

  menu: any;
  basePath: any;
  routePath: any;
  @Output() onToggleSideNavCanvas: EventEmitter<boolean> = new EventEmitter<boolean>();

  constructor(private readonly _menuService: MenuService, private readonly _router: Router) {
    this.menu = this._menuService.getMenuItems();
    console.log(this._router.url);
    let routes = this._router.url.substr(1).split("/");
    this.basePath = routes[0];
    this.routePath = this._router.url;
  }

  ngOnInit() {
  }

  ngAfterViewInit() {
    $(".side-nav-dropdown > a").click(function () {
      $(".side-nav-submenu-container").slideUp(200);
      if ($(this).parent().hasClass("active")) {
        $(".side-nav-dropdown").removeClass("active");
        $(this).parent().removeClass("active");
      } else {
        $(".side-nav-dropdown").removeClass("active");
        $(this).next(".side-nav-submenu-container").slideDown(200);
        $(this).parent().addClass("active");
      }
    });

    $('.side-nav-item.no-dropdown > a').click(function() {
      if ($(this).parent().hasClass('submenu-nav-item')) {
        $('.side-nav-item').removeClass('active right-border');
        $('.nav-item-link').removeClass('active');
        $(this).closest('.side-nav-dropdown').addClass("active right-border");
        $(this).closest('.side-nav-dropdown').find('.nav-item-main-header').addClass("active");
        $(this).addClass('active');
      } else {
        $('.side-nav-item').removeClass('active right-border');
        $('.nav-item-main-header').removeClass('active');
        $('.nav-item-link').removeClass('active');
        $(".side-nav-submenu-container").slideUp(200);
        $(this).addClass('active');
        $(this).parent().addClass("active right-border");
      }
    });

    $('.side-nav-menu').hover(function() {
      if ($('#sideNavContainer').hasClass('minimize-side-nav')) {
        $('#sideNavContainer').toggleClass('active');
        $('#sideNavContainer').toggleClass('hide-sub');
      } else {
        $('#sideNavContainer').removeClass('active');
      }
    });
  }

  toggleSideNavCanvas(): void {
    $('#sideNavContainer').toggleClass('minimize-side-nav hide-sub');
    this.onToggleSideNavCanvas.emit($('#sideNavContainer').hasClass('minimize-side-nav'));
    setTimeout(() => {
      window.dispatchEvent(new Event('resize'));
    }, 300);
  }

}
