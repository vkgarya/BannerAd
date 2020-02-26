import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { apiURL } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class BannerService {

  constructor(private readonly _http: HttpClient) { }

  addBanner(bannerData: FormData): Observable<any> {
    return this._http.post(`${apiURL}/create-ad`, bannerData, { withCredentials: true });
  }

  getBannerList(): Observable<any> {
    return this._http.get(`${apiURL}/ads/banner`, { withCredentials: true });
  }

  getBannerDetails(bannerId: number): Observable<any> {
    return this._http.get(`${apiURL}/ad/banner/${bannerId}`, { withCredentials: true });
  }

  deleteBanner(bannerId: number): Observable<any> {
    return this._http.delete(`${apiURL}/ad/banner/${bannerId}`, { withCredentials: true });
  }
}
