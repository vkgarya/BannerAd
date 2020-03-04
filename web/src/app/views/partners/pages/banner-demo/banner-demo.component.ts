import { Component, OnInit } from '@angular/core';
import { PartnersService } from 'src/app/core/services/partners/partners.service';

@Component({
  selector: 'app-banner-demo',
  templateUrl: './banner-demo.component.html',
  styleUrls: ['./banner-demo.component.scss']
})
export class BannerDemoComponent implements OnInit {

  bannerData: any;
  constructor(private readonly _partnerService: PartnersService) { }

  ngOnInit() {
    let bannerData = {
      "txn_id": "134",
      "ad_type": "banner",
      "limit": 4
    };
    this._partnerService.getBannerAd(bannerData).subscribe(data => {
      console.log(data);
      this.bannerData = data;
    });
  }

}
