import { Component, OnInit, Input, EventEmitter, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormArray, FormControl } from '@angular/forms';

import { isValidEmail } from 'src/app/utilities/FormInputValidators';
import { markFormGroupTouched } from 'src/app/utilities/MarkFormGroupAsTouched';
import { PartnersService } from 'src/app/core/services/partners/partners.service';
import { ManageUsersService } from 'src/app/core/services/manage-users/manage-users.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.scss']
})
export class EditUserComponent implements OnInit {

  @Input() set userId(userId: number) {
    if (userId) {
      this.getDetailsOfUser(userId);
    }
  }
  @Output() backToList: EventEmitter<any> = new EventEmitter<any>();
  editUserForm: FormGroup;
  userDetails: any;
  partnersList: any;
  disableEditUserButton = false;

  constructor(private readonly _formBuilder: FormBuilder, private readonly _partnerService: PartnersService, private readonly _manageusersService: ManageUsersService, private readonly _router: Router) { }

  ngOnInit() {

    this.getPartnersList();
    this.editUserForm = this._formBuilder.group({
      partners: [[], Validators.required],
      email: ['', [Validators.required, isValidEmail]],
      password: ['', Validators.required],
      //role: ['admin', Validators.required]
      // // role: ['admin', Validators.required]
      userRole: ['', Validators.required],
    });
  }


  getPartnersList(): void {
    this._partnerService.getPartnersList().subscribe(partnersList => {
      //console.log(partnersList);
      this.partnersList = partnersList;
    });
  }

  getDetailsOfUser(userId: number): void {
    this._manageusersService.getUserDetails(userId).subscribe(userDetails => {
      this.userDetails = userDetails;
      this.patchUserFields(this.userDetails);
    });
  }

  patchUserFields(userDetails: any): void {
    this.editUserForm.patchValue({

      partners: userDetails.partner_ids,
      email: userDetails.email,
      password: userDetails.password,
      userRole: userDetails.roles[0].name
    });
  }

  onEditUser(): any {
    if (this.editUserForm.valid) {
      this.disableEditUserButton = true;
      let addUserData = this.editUserForm.value;

      let userData: any = {

        partner_ids: addUserData.partners,
        email: addUserData.email,
        password: addUserData.password,
        //role_ids: (addUserData.userRole.role === 'admin') ? addUserData.userRole.role.value : [1],
       // role_ids: (addUserData.userRole.role === 'admin') ? addUserData.userRole.role.admin.value = [1] : addUserData.userRole.role.value = [2],
        role_ids: (addUserData.userRole === 'admin') ? [1] : [2],
        txn_id: 12345
      };
      


      this._manageusersService.updateUser(userData).subscribe(res => {
        alert('User created successfully!');
        this._router.navigate(['/admin/user-list']);
      }, err => {
        this.disableEditUserButton = false;
      });
      
    } else {
      markFormGroupTouched(this.editUserForm);
    }
  }

  // patchRoleFields(userDetails: any): any[] { 
  //   let userRole = [];
  // }

}
