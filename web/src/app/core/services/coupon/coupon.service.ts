import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { apiURL } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CouponService {

  constructor(private readonly _http: HttpClient) { }

  getCouponsList(): Observable<any> {
    return this._http.get(`${apiURL}/coupons`, {withCredentials: true});
  }

  createCoupon(couponData: any): Observable<any> {
    return this._http.post(`${apiURL}/coupon/create`, couponData, { withCredentials: true });
  }

  editCoupon(couponData: any): Observable<any> {
    return this._http.post(`${apiURL}/coupon/edit`, couponData, { withCredentials: true });
  }

  getCouponDetails(couponId: number): Observable<any> {
    return this._http.get(`${apiURL}/coupon/${couponId}`, { withCredentials: true });
  }

  deleteCoupon(couponId: number): Observable<any> {
    return this._http.delete(`${apiURL}/coupon/${couponId}`, { withCredentials: true });
  }
}
