import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

import { AuthenticationService } from 'src/app/core/services/authentication/authentication.service';
import { isValidEmail } from 'src/app/utilities/FormInputValidators';
import { markFormGroupTouched } from 'src/app/utilities/MarkFormGroupAsTouched';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  hidePassword = true;
  invalidCredentials = false;
  
  constructor(private readonly _authenticationService: AuthenticationService, private readonly _formBuilder: FormBuilder) { }

  ngOnInit() {
    this.loginForm = this._formBuilder.group({
      userName: ['', [Validators.required, isValidEmail]],
      password: ['', Validators.required]
    });
  }

  onLogin() {
    //this._authenticationService.login(null);
    if (this.loginForm.valid) {
      let loginData = {
        "email": this.loginForm.get('userName').value,
        "password": this.loginForm.get('password').value
      };
      
      this._authenticationService.login(loginData).subscribe(res => {
        this.invalidCredentials = false;
        this._authenticationService.setAppAuthToken(res.access_token);
      }, err => {
        this.invalidCredentials = true;
      });      
    } else {
      markFormGroupTouched(this.loginForm);
    }
  }

}
