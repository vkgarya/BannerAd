import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

import { AuthenticationService } from 'src/app/core/services/authentication/authentication.service';
import { isValidEmail } from 'src/app/utilities/FormInputValidators';
import { markFormGroupTouched } from 'src/app/utilities/MarkFormGroupAsTouched';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.scss']
})
export class ForgotPasswordComponent implements OnInit {
  forgotPasswordForm: FormGroup;
  
  constructor(private readonly _authenticationService: AuthenticationService,
              private readonly _formBuilder: FormBuilder ) { }

  ngOnInit() {
    this.forgotPasswordForm = this._formBuilder.group({
      email: ['', [Validators.required, isValidEmail]]
    });
  }

  onSubmit() {
    if (this.forgotPasswordForm.valid) {
      let email = this.forgotPasswordForm.get('email').value;
      
      this._authenticationService.forgotPassword(email).subscribe(res => {
        console.log("link sent to your mail.")
        alert('Reset password Link has been sent to your mail.');
        
    
      }, err => {
        console.log(err);
      });      
    } else {
      markFormGroupTouched(this.forgotPasswordForm);
    }
  }

}
