import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { apiURL } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CustomEventsService {

  constructor(private readonly _http: HttpClient) { }


  addCustomEvent(customEventData: FormData): Observable<any> {
    return this._http.post(`${apiURL}/event`, customEventData, { withCredentials: true });
  }

  
  updateCustomEvent(customEventData: FormData): Observable<any> {
    return this._http.put(`${apiURL}/event`, customEventData, { withCredentials: true });
  }

  getCustomEventsList(): Observable<any> {
    return this._http.get(`${apiURL}/events`, { withCredentials: true });
  }

  getAllCustomEventsList(): Observable<any> {
    return this._http.get(`${apiURL}/events`, { withCredentials: true });
  }

  getCustomEventDetails(eventId: number): Observable<any> {
    return this._http.get(`${apiURL}/event/${eventId}`, { withCredentials: true });
  }

  deleteCustomEvent(eventId: number): Observable<any> {
    return this._http.delete(`${apiURL}/event/${eventId}`, { withCredentials: true });
  }
}
