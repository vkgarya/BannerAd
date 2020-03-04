import { Component } from '@angular/core';
import { LoaderService } from './core/services/loader/loader.service';
import { NgxSpinnerService } from "ngx-spinner";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'Campaign';

  constructor(public readonly _loaderService: LoaderService, private readonly _spinner: NgxSpinnerService ) {
    //this._spinner.show();
    this._loaderService.isLoading.pipe().subscribe((isLoading) => {
      if (!isLoading) {
        setTimeout(() => {
          this._spinner.hide();
        }, 1);
      } else {
        setTimeout(() => {
          this._spinner.show();
        }, 1);
      }
    });
  }
}
