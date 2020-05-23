import {Links} from './links.model';
import {Project} from './Project.model';

export class UserData {
  userName: string;
  oauthClientId: string;
  oauthClientSecret: string;
  nav: string;
  // tslint:disable-next-line:variable-name
  _links: Links;
  currentProject: Project;
  defaultLang: string;

  constructor() {
    this.userName = '';
    this.oauthClientId = 'grouse-client';
    this.oauthClientSecret = 'secret';
    this.nav = '';
    this._links = new Links();
    this.currentProject = null;
    this.defaultLang = 'Bokmaal';
  }
}
