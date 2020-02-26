import { Component, OnInit } from '@angular/core';

import { AuthenticationService } from '../../services/authentication/authentication.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  languages = [
    // {id: 1, name: 'المتحدة', flagImage: 'assets/images/uae_flag.png'},
    //{id: 2, name: 'Hindi', flagImage: 'assets/images/uae_flag.png'},
    {id: 3, name: 'English', flagImage: 'assets/images/uae_flag.png'}
  ];
  selectedLanguage = this.languages[0];

  constructor(private readonly _authenticationService: AuthenticationService) { }

  ngOnInit() {
  }

  onChangeLanguage(language: any): void {
    this.selectedLanguage = language;
  }

  logout(): void {
    this._authenticationService.logout();
  }

}
