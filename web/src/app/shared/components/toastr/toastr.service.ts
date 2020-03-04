import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { filter } from 'rxjs/operators';

import { Toastr, ToastrType } from './toastr.model';

@Injectable({ providedIn: 'root' })
export class ToastrService {
    private subject = new Subject<Toastr>();
    private defaultId = 'default-toastr';

    // enable subscribing to Toastrs observable
    onToastr(id = this.defaultId): Observable<Toastr> {
        return this.subject.asObservable().pipe(filter(x => x && x.id === id));
    }

    // convenience methods
    success(message: string, options?: any) {
        this.toastr(new Toastr({ ...options, type: ToastrType.Success, message }));
    }

    error(message: string, options?: any) {
        this.toastr(new Toastr({ ...options, type: ToastrType.Error, message }));
    }

    info(message: string, options?: any) {
        this.toastr(new Toastr({ ...options, type: ToastrType.Info, message }));
    }

    warn(message: string, options?: any) {
        this.toastr(new Toastr({ ...options, type: ToastrType.Warning, message }));
    }

    // main Toastr method
    toastr(toastr: Toastr) {
        toastr.id = toastr.id || this.defaultId;
        this.subject.next(toastr);
    }

    // clear Toastrs
    clear(id = this.defaultId) {
        this.subject.next(new Toastr({ id }));
    }
}
