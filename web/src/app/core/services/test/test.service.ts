import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CookieService } from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root'
})
export class TestService {

  constructor(private readonly _http: HttpClient, private readonly _cs: CookieService) { }

  test(data): Observable<any> {
    return this._http.post(``, data);
  }

  checkApi(): Observable<any> {
    //this._cs.set('at', 'token');
    return this._http.get(`http://localhost:5000/users`, { withCredentials: true });
  }
}
