import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormArray, FormControl } from '@angular/forms';

import { isValidEmail } from 'src/app/utilities/FormInputValidators';
import { markFormGroupTouched } from 'src/app/utilities/MarkFormGroupAsTouched';
import { PartnersService } from 'src/app/core/services/partners/partners.service';
import { ManageUsersService } from 'src/app/core/services/manage-users/manage-users.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.scss']
})
export class AddUserComponent implements OnInit {

  addUserForm: FormGroup;
  disableAddUserButton = false;
  // partnersList = [
  //   { label: 1, value: 1  },
  //   { label: 2, value: 2  },
  //   { label: 3, value: 3  },
  //   { label: 4, value: 4  },
  //   { label: 5, value: 5  }
  // ];
partnersList: any;

  constructor(private readonly _formBuilder: FormBuilder, private readonly _partnerService: PartnersService, private readonly _manageusersService: ManageUsersService, private readonly _router: Router) { }

  ngOnInit() {
    this.getPartnersList();
    this.addUserForm = this._formBuilder.group({
      partners: [[], Validators.required],
      email: ['', [Validators.required, isValidEmail]],
      password: ['', Validators.required],
      //role: ['admin', Validators.required]
      // // role: ['admin', Validators.required]
      userRole: ['admin', Validators.required],
    });
  }

  
  getPartnersList(): void {
    this._partnerService.getPartnersList().subscribe(partnersList => {
      //console.log(partnersList);
      this.partnersList = partnersList;
    });
  }

  onAddUser(): any {
    if (this.addUserForm.valid) {
      this.disableAddUserButton = true;
      let addUserData = this.addUserForm.value;

      let userData: any = {

        partner_ids: addUserData.partners,
        email: addUserData.email,
        password: addUserData.password,
        //role_ids: (addUserData.userRole.role === 'admin') ? addUserData.userRole.role.value : [1],
       // role_ids: (addUserData.userRole.role === 'admin') ? addUserData.userRole.role.admin.value = [1] : addUserData.userRole.role.value = [2],
        role_ids: (addUserData.userRole === 'admin') ? [1] : [2],
        txn_id: 12345
      };
      


      this._manageusersService.addUser(userData).subscribe(res => {
        alert('User created successfully!');
        this._router.navigate(['/admin/user-list']);
      }, err => {
        this.disableAddUserButton = false;
      });
      
    } else {
      markFormGroupTouched(this.addUserForm);
    }
  }
}
