import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'src/app/shared/components/toastr/toastr.service';

import { ConfirmDialogComponent, ConfirmDialogModel } from 'src/app/shared/components/confirm-dialog/confirm-dialog.component';
import { MatDialog } from '@angular/material';

@Component({
  selector: 'app-partners',
  templateUrl: './partners.component.html',
  styleUrls: ['./partners.component.scss']
})
export class PartnersComponent implements OnInit {

  //Vijay - Confirm Dialog
  result: string = '';
  //Vijay - Toastr
  options = {
    autoClose: true,
    keepAfterRouteChange: false
  };

  constructor(public dialog: MatDialog, protected readonly toastrService: ToastrService) { }

  ngOnInit() {
  }

  //Vijay - Confirm Dialog
  confirmDialog(): void {
    const message = `Are you sure you want to do this?`;

    const dialogData = new ConfirmDialogModel("Confirm Action", message);

    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      maxWidth: "400px",
      data: dialogData
    });

    dialogRef.afterClosed().subscribe(dialogResult => {
      this.result = dialogResult;
    });
  }

}
