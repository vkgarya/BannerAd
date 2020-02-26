import { Component, OnInit, ViewChild, TemplateRef } from '@angular/core';
import { PartnersService } from 'src/app/core/services/partners/partners.service';
import { ManageUsersService } from 'src/app/core/services/manage-users/manage-users.service';
import { DatatableComponent } from '@swimlane/ngx-datatable';
import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';

@Component({
  selector: 'app-manage-user-list',
  templateUrl: './manage-user-list.component.html',
  styleUrls: ['./manage-user-list.component.scss']
})
export class ManageUserListComponent implements OnInit {
  action = 'list';

  temp = [];
  columns = [

    { prop: 'id', name: 'Id' },
    { prop: 'email', name: 'Email', flexSize: 1 },
    { prop: 'roles', name: 'Role', flexSize: 1 },
    //{ prop: 'partners', name: 'Partners', flexsize: 1 },
    { prop: 'active', name: 'Status', flexSize: 1 },


  ];
  pageTitleInfo = {
    title: 'Users',
    icon: 'assets/images/ad_user.png'
  };

  //rows: any;
  usersList: any;
  selectedUser: any;


  @ViewChild(DatatableComponent, { static: false }) table: DatatableComponent;
  modalRef: BsModalRef;


  constructor(private readonly _manageusersService: ManageUsersService, private readonly _partnerService: PartnersService, private readonly modalService: BsModalService) { }

  ngOnInit() {
    this.getUsersList();
  }


  getUsersList(): void {
    this._manageusersService.getUsersList().subscribe(usersList => {
      console.log(usersList);
      //this.usersList = this.usersList.filter(usersList => usersList.is_active);
      this.usersList = usersList;
      console.log(this.usersList);
    });
  }

  renderRoles(roles: any): string {
    let roleString = [];
    roles.forEach(role => {
      roleString.push(role.displayName);
    });

    return roleString.join();
  }

  openUserDetailModal(modalTemplate: TemplateRef<any>, user: any): void {
    this.modalRef = this.modalService.show(modalTemplate);
    this.selectedUser = user;
  }

  onEditUser(user: any): void {
    this.action = 'edit';
    this.pageTitleInfo.title = 'Admin/Edit User';
    this.selectedUser = user;
  }

  onDeleteUser(user: any): void {
    if (confirm('Are you sure want to delete?')) {
      this._manageusersService.deleteUser(user.id).subscribe(res => {
        alert("User removed successfully!");
        this.getUsersList();
      });
    }
  }


  backToList(): void {
    this.pageTitleInfo.title = "Manage-Users";
    this.getUsersList();
    this.action = 'list';
  }


}
