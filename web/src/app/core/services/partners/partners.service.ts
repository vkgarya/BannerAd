import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { apiURL } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PartnersService {

  constructor(private readonly _http: HttpClient) { }

  // getPartners(): Observable<any> {
  //   return this._http.get(`http://localhost:5000/api`);
  // }

  addPartner(partnerData: FormData): Observable<any> {
    return this._http.post(`${apiURL}/partner`, partnerData, { withCredentials: true });
  }

  updatePartner(partnerData: FormData): Observable<any> {
    return this._http.put(`${apiURL}/partner`, partnerData, { withCredentials: true });
  }

  getPartnersList(): Observable<any> {
    return this._http.get(`${apiURL}/partners`, { withCredentials: true });
  }

  // getUsersList(): Observable<any> {
  //   return this._http.get(`${apiURL}/users`);
  // }
  // getUserDetails(userId: number): Observable<any> {
  //   return this._http.get(`${apiURL}/user/${userId}`);
  // }

  getPartnerDetails(partnerId: number): Observable<any> {
    return this._http.get(`${apiURL}/partner/${partnerId}`, { withCredentials: true });
  }
  
  deletePartner(partnerId: number): Observable<any> {
    return this._http.delete(`${apiURL}/partner/${partnerId}`, { withCredentials: true });
  }

  //Vijay
  getBannerAd(bannerData: any): Observable<any> { 
    return this._http.post(`http://api.empay.we4global.com:8080/v1/ad-request`,bannerData, { withCredentials: true });
  }
}
