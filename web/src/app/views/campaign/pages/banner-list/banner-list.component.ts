import { Component, OnInit, ViewChild, TemplateRef } from '@angular/core';
import { DatatableComponent } from '@swimlane/ngx-datatable';
import { PartnersService } from 'src/app/core/services/partners/partners.service';

import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';
import { BannerService } from 'src/app/core/services/banner/banner.service';

@Component({
  selector: 'app-banner-list',
  templateUrl: './banner-list.component.html',
  styleUrls: ['./banner-list.component.scss']
})
export class BannerListComponent implements OnInit {

  temp = [];
  action = 'list';
  columns = [ 
    { prop: 'id', name: 'ID', flexSize: 1 },
    { prop: 'banners', name: 'Image', flexSize: 2 },
    { prop: 'click_type', name: 'Click Type', flexSize: 2 },
    { prop: 'balance_amount', name: 'Balance Amount(AED)', flexSize: 2 },
    { prop: 'balance_clicks', name: 'Balance Clicks', flexSize: 2 },
    { prop: 'started_on', name: 'From', flexSize: 2 },
    { prop: 'closed_on', name: 'To', flexSize: 2 },
    { prop: 'status', name: 'Status', flexSize: 2 }
  ];

  pageTitleInfo = {
    title: 'Banner Campaign',
    icon: 'assets/images/banner_campaign.png'
  };

  bannerList: any;
  selectedBanner: any;
  
  @ViewChild(DatatableComponent, { static: false }) table: DatatableComponent;
  modalRef: BsModalRef;


  constructor(private readonly _partnerService: PartnersService, private readonly modalService: BsModalService, private readonly _bannerService: BannerService) { }

  ngOnInit() {
    this.getBannerList();
  }

  getBannerList(): void {
    this._bannerService.getBannerList().subscribe(bannerList => {
      console.log(bannerList);
      this.bannerList = bannerList.results;
    })
  }
  
  openBannerDetailModal(modalTemplate: TemplateRef<any>, banner: any): void {
    this.modalRef = this.modalService.show(modalTemplate);
    this.selectedBanner = banner;
  }

  onEditBanner(banner: any): void {
    this.action = 'edit';
    this.pageTitleInfo.title = 'Campaign/Edit Banner campaign';
    this.selectedBanner = banner;
  }

  onDeleteBanner(banner: any): void {
    if (confirm('Are you sure want to delete?')) {
      this._bannerService.deleteBanner(banner.id).subscribe(res => {
        alert("Banner removed successfully!");
        this.getBannerList();
      });
    }
  }

  backToList(): void { 
    this.pageTitleInfo.title = "Banner Campaign";
    this.getBannerList();
    this.action = 'list';
  }

}
