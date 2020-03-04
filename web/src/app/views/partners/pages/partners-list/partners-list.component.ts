import { Component, OnInit, ViewChild, TemplateRef } from '@angular/core';
import { PartnersService } from 'src/app/core/services/partners/partners.service';
import { DatatableComponent } from '@swimlane/ngx-datatable';

import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';

import { ConfirmDialogComponent, ConfirmDialogModel } from 'src/app/shared/components/confirm-dialog/confirm-dialog.component';
import { AlertDialogComponent, AlertDialogModel } from 'src/app/shared/components/alert-dialog/alert-dialog.component';
import { MatDialog } from '@angular/material';

@Component({
  selector: 'app-partners-list',
  templateUrl: './partners-list.component.html',
  styleUrls: ['./partners-list.component.scss']
})
export class PartnersListComponent implements OnInit {


  temp = [];
  action = 'list';
  columns = [
    { prop: 'name', name: 'Name', flexSize: 2 },
    { prop: 'email', name: 'Email', flexSize: 1 },
    { prop: 'phone_no', name: 'Phone No', flexSize: 1 },
    { prop: 'status', name: 'Status', flexSize: 1 }
  ];
  pageTitleInfo = {
    title: 'Partners',
    icon: 'assets/images/partners.png'
  };

  partnersList: any;
  selectedPartner: any;

  @ViewChild(DatatableComponent, { static: false }) table: DatatableComponent;
  modalRef: BsModalRef;

  constructor(private readonly _partnerService: PartnersService, private readonly modalService: BsModalService, private readonly dialog: MatDialog ) { }

  ngOnInit() {
    this.getPartnersList();
  }

  getPartnersList(): void {
    this._partnerService.getPartnersList().subscribe(partnersList => {
      console.log(partnersList);
      this.partnersList = partnersList;
    });
  }

  fetch(cb) {
    const req = new XMLHttpRequest();
    req.open('GET', `assets/data/company.json`);

    req.onload = () => {
      cb(JSON.parse(req.response));
    };

    req.send();
  }

  updateFilter(event) {
    const val = event.target.value.toLowerCase();

    // filter our data
    const temp = this.temp.filter(function (d) {
      return d.name.toLowerCase().indexOf(val) !== -1 || !val;
    });

    // update the rows
    //this.rows = temp;
    // Whenever the filter changes, always go back to the first page
    this.table.offset = 0;
  }

  openPartnerDetailModal(modalTemplate: TemplateRef<any>, partner: any): void {
    this.modalRef = this.modalService.show(modalTemplate);
    this.selectedPartner = partner;
  }

  onEditPartner(partner: any): void {
    this.action = 'edit';
    this.pageTitleInfo.title = 'Partners/Edit partner details';
    this.selectedPartner = partner;
  }


  onDeletePartner(partner: any): void {
    this.dialog.open(ConfirmDialogComponent, {
      maxWidth: "400px",
      data: new ConfirmDialogModel(`Confirm Deletion`, `Are you sure want to delete?`)
    }).afterClosed().subscribe(confirmDialogResult => {
      if (confirmDialogResult) { 
          this._partnerService.deletePartner(partner.id).subscribe(res => {
          // alert("Partner removed successfully!");
          this.dialog.open(AlertDialogComponent, {
            maxWidth: "400px",
            data: new AlertDialogModel(`Information`, `Partner removed successfully!`)
          }).afterClosed().subscribe(alertDialogResult => {
            if (alertDialogResult) { 
              this.getPartnersList();
            }
          });
          this.getPartnersList();
        });
      }
    });
    // if (confirm('Are you sure want to delete?')) {
    //   this._partnerService.deletePartner(partner.id).subscribe(res => {
    //     alert("Partner removed successfully!");
    //     this.getPartnersList();
    //   });
    // }
  }

  backToList(): void { 
    this.pageTitleInfo.title = "Partners";
    this.getPartnersList();
    this.action = 'list';
  }

}