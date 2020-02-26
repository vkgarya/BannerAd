import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormArray } from '@angular/forms';
import { isValidEmail, isValidUrlWebsite, customPhoneNoValidation } from 'src/app/utilities/FormInputValidators';
import { markFormGroupTouched } from 'src/app/utilities/MarkFormGroupAsTouched';
import { PartnersService } from 'src/app/core/services/partners/partners.service';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/core/services/authentication/authentication.service';
@Component({
  selector: 'app-edit-partner',
  templateUrl: './edit-partner.component.html',
  styleUrls: ['./edit-partner.component.scss']
})
export class EditPartnerComponent implements OnInit {
  @Input() set partnerId(partnerId: number) {
    if (partnerId) {
      this.getDetailsOfPartner(partnerId);
    }
  }
  @Output() backToList: EventEmitter<any> = new EventEmitter<any>();
  editPartnerForm: FormGroup;
  partnerDetails: any;
  disableEditPartnerButton = false;

  constructor(private readonly _formBuilder: FormBuilder, private _auth: AuthenticationService, private readonly _partnersService: PartnersService, private readonly _router: Router) { }

  ngOnInit() {

    this.editPartnerForm = this._formBuilder.group({
      partner: ['', Validators.required],
      merchant: ['',Validators.required],
      companyName: ['', Validators.required],
      status: ['active', Validators.required],
      email: ['', [Validators.required, isValidEmail]],
      phoneNumber: ['', [Validators.required, customPhoneNoValidation]],
      websiteUrl: ['', [Validators.required, isValidUrlWebsite]],
      natureOfBussiness: ['', Validators.required],
      address: ['', Validators.required]
    });
  }

  getDetailsOfPartner(partnerId: number): void {
    this._partnersService.getPartnerDetails(partnerId).subscribe(partnerDetails => {
      this.partnerDetails = partnerDetails;
      this.patchPartnerFields(this.partnerDetails);
    });
  }

  patchPartnerFields(partnerDetails: any): void {
    this.editPartnerForm.patchValue({
      partner: partnerDetails.name,
      merchant: partnerDetails.merchant_id,
      companyName: partnerDetails.company_name,
      status: partnerDetails.status,
      email: partnerDetails.email,
      phoneNumber: partnerDetails.phone_no,
      websiteUrl: partnerDetails.website,
      natureOfBussiness: partnerDetails.nature_of_business,
      address: partnerDetails.address   
    });
  }


  onEditPartner(): any {
    if (this.editPartnerForm.valid) {
      this.disableEditPartnerButton = true;
      let addPartnerData = this.editPartnerForm.value;

      let partnerData: any = {
        id: this.partnerDetails.id,
        name: addPartnerData.partner,
        merchant_id: addPartnerData.merchant,
        company_name: addPartnerData.companyName,
        status: addPartnerData.status,
        email: addPartnerData.email,
        phone_no: addPartnerData.phoneNumber,
        website: addPartnerData.websiteUrl,
        nature_of_business: addPartnerData.natureOfBussiness,
        address: addPartnerData.address
      };

      this._partnersService.updatePartner(partnerData).subscribe(res => {
        alert('Partner updated successfully!');
        this.onBackToList();
      }, err => {
        this.disableEditPartnerButton = false;
      });
    } else {
      markFormGroupTouched(this.editPartnerForm);
    }
  }

  onBackToList(): void {
    this.backToList.emit();
  }

}
