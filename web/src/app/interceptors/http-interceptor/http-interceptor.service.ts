import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { delay, retry } from 'rxjs/operators';

import { LoaderService } from 'src/app/core/services/loader/loader.service';
import { AuthenticationService } from 'src/app/core/services/authentication/authentication.service';

@Injectable({
  providedIn: 'root'
})
export class HttpInterceptorService implements HttpInterceptor {

  httpRequests: HttpRequest<any>[] = [];
  
  constructor(private readonly _loaderService: LoaderService, private readonly _authenticationService: AuthenticationService) { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = this._authenticationService.getAppAuthToken();

    let authorizedReq = req;

    /*if (token) {
      authorizedReq = req.clone({
        withCredentials: true
      });
    }*/
      
    this.httpRequests.push(authorizedReq);
    this._loaderService.changeLoaderStatus(true);
    return Observable.create(observer => {
      const subscription = next.handle(authorizedReq)
          .pipe(
            //retry(1)
          )
          .subscribe(
              event => {
                  if (event instanceof HttpResponse) {
                      this.removeRequest(authorizedReq);
                      observer.next(event);
                  }
              },
              err => {
                  //alert('error returned');
                  this.removeRequest(authorizedReq);
                  observer.error(err);
              },
              () => {
                  this.removeRequest(authorizedReq);
                  observer.complete();
              });
      // remove request from queue when cancelled
      return () => {
          this.removeRequest(authorizedReq);
          subscription.unsubscribe();
      };
    });
  }

  removeRequest(req: HttpRequest<any>): void {
    const i = this.httpRequests.indexOf(req);
    if (i >= 0) {
      this.httpRequests.splice(i, 1);
    }
    this._loaderService.changeLoaderStatus(this.httpRequests.length > 0);
  }
}
