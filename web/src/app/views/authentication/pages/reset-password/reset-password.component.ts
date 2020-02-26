import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { AuthenticationService } from 'src/app/core/services/authentication/authentication.service';
import { isValidEmail } from 'src/app/utilities/FormInputValidators';
import { PasswordValidator } from  './password.validator';
import { ResetPasswordRequest } from  './reset-Password-request';
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-forgot-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.scss']
})
export class ResetPasswordComponent implements OnInit {
  token: string;
  hidePassword = true;
  resetPasswordForm = this._formBuilder.group({
    email: ['', [Validators.required, isValidEmail]],
    password: ['', Validators.required],
    confirmPassword: ['']
  }, { validator: [PasswordValidator] });
  
  constructor(private readonly _authenticationService: AuthenticationService,
              private readonly _formBuilder: FormBuilder,
              private router: Router,
              private route: ActivatedRoute) { }

  ngOnInit() {
    this.token = this.route.snapshot.paramMap.get('token');
  }

  resetPassword() {
    if (!this.resetPasswordForm.invalid) {
      const data = new ResetPasswordRequest();

      data.token = this.token;
      data.email = this.resetPasswordForm.value.email;
      data.password = this.resetPasswordForm.value.password;

      this._authenticationService.resetPassword(data).subscribe((response: any) => {
        console.log('Password Reset successfully');
        alert('Password Reset successfully');
        this.router.navigate(['/login']);
      }, error => {
        console.log('Password Not Reset');
      });
    }
  }

  get password() { return this.resetPasswordForm.get('password'); }
}
