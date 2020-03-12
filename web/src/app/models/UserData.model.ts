import {Link} from './link.model';
import {Links} from './links.model';
import {Project} from './Project.model';

export class UserData {
  loginAdress: string;
  userName: string;
  oauthClientId: string;
  oauthClientSecret: string;
  nav: string;
  // tslint:disable-next-line:variable-name
  _links: Links;
  currentProject: Project;

  constructor() {
    this.loginAdress = '';
    this.userName = '';
    this.oauthClientId = 'grouse-client';
    this.oauthClientSecret = 'secret';
    this.nav = '';
    this._links = null;
    this.currentProject = null;
  }
}
