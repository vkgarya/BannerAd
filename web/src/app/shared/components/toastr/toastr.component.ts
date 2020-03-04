import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { Router, NavigationStart } from '@angular/router';
import { Subscription } from 'rxjs';

import { Toastr, ToastrType } from './toastr.model';
import { ToastrService } from './toastr.service';

@Component({
  selector: 'toastr',
  templateUrl: './toastr.component.html',
  styleUrls: ['./toastr.component.scss']
})
export class ToastrComponent implements OnInit {

    @Input() id = 'default-toastr';
    @Input() fade = true;

    toastrs: Toastr[] = [];
    toastrSubscription: Subscription;
    routeSubscription: Subscription;

    constructor(private router: Router, private toastrService: ToastrService) { }

    ngOnInit() {
        // subscribe to new toastr notifications
        this.toastrSubscription = this.toastrService.onToastr(this.id)
            .subscribe(toastr => {
                // clear toastrs when an empty toastr is received
                if (!toastr.message) {
                    // filter out toastrs without 'keepAfterRouteChange' flag
                    this.toastrs = this.toastrs.filter(x => x.keepAfterRouteChange);

                    // remove 'keepAfterRouteChange' flag on the rest
                    this.toastrs.forEach(x => delete x.keepAfterRouteChange);
                    return;
                }

                // add toastr to array
                this.toastrs.push(toastr);

                // auto close toastr if required
                if (toastr.autoClose) {
                    setTimeout(() => this.removeToastr(toastr), 10000);
                }
           });

        // clear toastrs on location change
        this.routeSubscription = this.router.events.subscribe(event => {
            if (event instanceof NavigationStart) {
                this.toastrService.clear(this.id);
            }
        });
    }

    ngOnDestroy() {
        // unsubscribe to avoid memory leaks
        this.toastrSubscription.unsubscribe();
        this.routeSubscription.unsubscribe();
    }

    removeToastr(toastr: Toastr) {
        if (this.fade) {
            // fade out toastr
            this.toastrs.find(x => x === toastr).fade = true;

            // remove toastr after faded out
            setTimeout(() => {
                this.toastrs = this.toastrs.filter(x => x !== toastr);
            }, 250);
        } else {
            // remove toastr
            this.toastrs = this.toastrs.filter(x => x !== toastr);
        }
    }

    cssClass(toastr: Toastr) {
        if (!toastr) return;

        const classes = ['alert', 'alert-dismissable'];

        const toastrTypeClass = {
            [ToastrType.Success]: 'alert alert-success',
            [ToastrType.Error]: 'alert alert-danger',
            [ToastrType.Info]: 'alert alert-info',
            [ToastrType.Warning]: 'alert alert-warning'
        }

        classes.push(toastrTypeClass[toastr.type]);

        if (toastr.fade) {
            classes.push('fade');
        }

        return classes.join(' ');
    }
}
