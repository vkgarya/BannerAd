import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';

import { apiURL, DOMAIN } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(private readonly _http: HttpClient, private readonly _router: Router, private readonly _cookieService: CookieService) { }

  login(loginData: any): any {
    return this._http.post(`${apiURL}/login`, loginData);
  }

  forgotPassword(email: any) {
    let url = `${apiURL}/forgot-password/` + email;
    return this._http.post(url, {});
  }

  logout() {
    this.removeAppAuthToken();
  }

  redirectToHome(): void {
    this._router.navigate(['/partners']);
  }

  getUserDetails(): Observable<any> {
    return this._http.get(`${apiURL}/me`, { withCredentials: true });
  }

  setAppAuthToken(token: string): void {
    this._cookieService.set('at', `${token}`, null, '/');
    this.getUserDetails().subscribe(userDetails => {
      //console.log(userDetails);
      this.setUserId(userDetails.id);
      this.redirectToHome();
    }, err => {
      console.log(err);
      //this.redirectToHome();
    });
  }

  getAppAuthToken(): string {
    return this._cookieService.get('at');
  }

  isLoggedIn(): boolean {
    let userToken = this.getAppAuthToken();
    return userToken !== null && userToken !== '';
  }

  setUserId(userId: any): void {
    localStorage.setItem('userId', userId);
  }

  getUserId(): string {
    return localStorage.getItem('userId');
  }

  removeAppAuthToken(): void {
    this._cookieService.delete('at', '/');
    this._router.navigate(['/login']);
  }

  resetPassword(data: any) {
    return this._http.post(`${apiURL}/reset-password`, data);
  }
}
