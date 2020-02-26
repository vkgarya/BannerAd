import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
//import { TestService } from 'src/app/core/services/test/test.service';
import { isValidEmail, isValidUrlWebsite, customPhoneNoValidation } from 'src/app/utilities/FormInputValidators';
import { markFormGroupTouched } from 'src/app/utilities/MarkFormGroupAsTouched';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/core/services/authentication/authentication.service';
import { PartnersService } from 'src/app/core/services/partners/partners.service';

@Component({
  selector: 'app-add-partner',
  templateUrl: './add-partner.component.html',
  styleUrls: ['./add-partner.component.scss']
})
export class AddPartnerComponent implements OnInit {

  addPartnerForm: FormGroup;
  disableAddPartnerButton = false;

  constructor(private readonly _formBuilder: FormBuilder, private _auth: AuthenticationService, private readonly _partnersService: PartnersService, private readonly _router: Router) { }

  ngOnInit() {
    this.addPartnerForm = this._formBuilder.group({
      partner: ['', Validators.required],
      merchant: ['', Validators.required],
      companyName: ['', Validators.required],
      status: ['active', Validators.required],
      email: ['', [Validators.required, isValidEmail]],
      phoneNumber: ['', [Validators.required, customPhoneNoValidation]],
      websiteUrl: ['', [Validators.required, isValidUrlWebsite]],
      natureOfBussiness: ['', Validators.required],
      address: ['', Validators.required]
    });
  }

  onAddPartner(): any {
    if (this.addPartnerForm.valid) {
      this.disableAddPartnerButton = true;
      
      let addPartnerData = this.addPartnerForm.value;

      let partnerData: any = {
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

      this._partnersService.addPartner(partnerData).subscribe(res => {
        alert('Partner created successfully!');
        this._router.navigate(['/partners/partners-list']);
      }, err => {
        this.disableAddPartnerButton = false;
      });
    }else {
      markFormGroupTouched(this.addPartnerForm);
    }
  }

  checkApi(): void {
    // this._testService.checkApi().subscribe(res => {
    //   console.log(res);
    // });
    this._auth.getUserDetails().subscribe(userDetails => {
      console.log(userDetails);
    }, err => {
    });
  }

}
