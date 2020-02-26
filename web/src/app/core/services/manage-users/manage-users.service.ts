import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { apiURL } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ManageUsersService {

  constructor(private readonly _http: HttpClient) { }


  addUser(userData: FormData): Observable<any> {
    return this._http.post(`${apiURL}/add-user`, userData, { withCredentials: true });
  }

  // getUsersList(): Observable<any> {
  //   return this._http.get(`${apiURL}/users`);
  // }

  // getUserDetails(): Observable<any> {
  //   return this._http.get(`${apiURL}/user/{userId}`);
  // }

  getUsersList(): Observable<any> {
    return this._http.get(`${apiURL}/users`, { withCredentials: true });
  }

  getUserDetails(userId: number): Observable<any> {
    return this._http.get(`${apiURL}/user/${userId}`, { withCredentials: true });
  }
  updateUser(userData: FormData): Observable<any> {
    return this._http.put(`${apiURL}/update-user`, userData, { withCredentials: true });
  }


  deleteUser(userId: number): Observable<any> {
    return this._http.post(`${apiURL}/user-partner/${userId}`, { withCredentials: true });
  }

}
