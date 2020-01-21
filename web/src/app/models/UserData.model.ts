export class UserData {
  loginAdress: string;
  logoutAdress: string;
  userAdress: string;
  oauthClientId: string;
  oauthClientSecret: string;

  constructor() {
    this.loginAdress = '';
    this.logoutAdress = '';
    this.userAdress = '';
    this.oauthClientId = 'grouse-client';
    this.oauthClientSecret = 'secret';
  }
}
