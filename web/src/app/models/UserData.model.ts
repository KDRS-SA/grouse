import {Link} from './link.model';
import {Links} from './links.model';

export class UserData {
  loginAdress: string;
  logoutAdress: string;
  userAdress: string;
  userName: string;
  oauthClientId: string;
  oauthClientSecret: string;
  nav: string;
  // tslint:disable-next-line:variable-name
  _links: Links;
  currentProject;

  constructor() {
    this.loginAdress = '';
    this.logoutAdress = '';
    this.userAdress = '';
    this.userName = '';
    this.oauthClientId = 'grouse-client';
    this.oauthClientSecret = 'secret';
    this.nav = '';
    this._links = null;
    this.currentProject = null;
  }
}
