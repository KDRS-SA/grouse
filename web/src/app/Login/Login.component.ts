import {Component, Input, OnInit} from '@angular/core';
import {Animations} from '../app.animations';
import {FormGroup, FormBuilder, FormControl, Validators} from '@angular/forms';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {RegisterModel} from '../models/register.model';
import {LoginModel} from '../models/login.model';
import {Router} from '@angular/router';
import {UserData} from '../models/UserData.model';
import {MatSnackBar} from '@angular/material';
import {MatDialog, MatDialogRef} from '@angular/material/dialog';
import {TranslateService} from "@ngx-translate/core";
import {startUrl} from '../common';
import {Links} from '../models/links.model';

@Component({
  selector: 'app-root',
  templateUrl: './Login.component.html',
  styleUrls: [
    './Login.component.css',
    '../common.css'
  ],
  animations: [
    Animations.toggleSlide,
    Animations.shake
  ]
})

export class LoginComponent implements  OnInit {

  constructor(http: HttpClient, private formBuilder: FormBuilder, router: Router, snackBar: MatSnackBar, public dialog: MatDialog, public translate: TranslateService) {
    this.login = true;
    this.http = http;
    this.router = router;
    this.userData = new UserData();
    this.shake = false;
    this.snackBar = snackBar;
    translate.addLangs(['Bokmål', 'English', 'Nynorsk']);
    translate.setDefaultLang('Bokmål');
  }

  public title = 'Grouse';
  public login: boolean;

  regUser: RegisterModel = new RegisterModel();
  loginUser: LoginModel = new LoginModel();
  registerForm: FormGroup;
  loginForm: FormGroup;
  shake: boolean;

  private http: HttpClient;
  private router: Router;
  private userData: UserData;
  private snackBar: MatSnackBar;

  // Email validator
  email = new FormControl('',
    [Validators.required, Validators.email]);

  @Input()
  Checked: boolean;

  /**
   * getErrorMessage
   * Fetches error message for the form to display if not valid
   */
  getErrorMessage() {
    // @ts-ignore
    // tslint:disable-next-line:max-line-length
    return this.email.hasError('required') ? this.translate.get('ErrorsAndWarns.MustEnterEmail').value : this.email.hasError('email') ? this.translate.get('ErrorsAndWarns.InnvalidEmail').value : '';
  }

  /**
   * ngOnInit
   * Runs inital code for the page when it loads, builds form validators and fetches userData from local storage
   */
  ngOnInit() {
    this.registerForm = this.formBuilder.group({
      email: [this.regUser.email, [
        Validators.required,
        Validators.email
      ]],
      password: [this.regUser.password, [
        Validators.required,
        Validators.minLength(4),
        Validators.maxLength(30)
      ]],
      passwordRepeat: [this.regUser.passwordRepeat, [
        Validators.required,
        Validators.minLength(4),
        Validators.maxLength(30)
      ]],
      checkBox: [this.regUser.checkBox, [
        Validators.requiredTrue
      ]]
    });

    this.loginForm = this.formBuilder.group({
      email: [this.loginUser.email, [
        Validators.required,
        Validators.email
      ]],
      password: [this.loginUser.password, [
        Validators.required
      ]]
    });
    this.userData = JSON.parse(localStorage.getItem('UserData'));
  }

  /**
   * ReadGDPR
   * Opens the dialogbox containing the GDPR statment
   */
  ReadGDPR() {
    this.dialog.open(GDPRContent);
  }

  /**
   * registerSubmitt
   * Sends a request to the server requesting the creation of a new user with information from this class that is edited by the user
   */
  registerSubmit() {
    this.shake = false;

    if (this.regUser.password === this.regUser.passwordRepeat) { // Checks that passwords match

      // If everything is in order, sends the data to server
      const body: INewUser = {
        username: this.regUser.email,
        password: this.regUser.password
      };
      this.http.post(this.userData._links['create-user'].href, body).subscribe(
        result => {
          // The transmission was a succsess and the server accepted the new user
          this.loginUser.password = this.regUser.password;
          this.loginUser.email = this.regUser.email;
          this.loginSubmit();
        }, error => {
          // There was an error

          // A user with the email entered allready exists
          if (error.error.message.startsWith('No GrouseUser exists')) {
            this.shake = true;
            // @ts-ignore
            // tslint:disable-next-line:max-line-length
            this.snackBar.open(this.translate.get('ErrorsAndWarns.UserAlreadyExists').value, this.translate.get('ErrorsAndWarns.Close').value);
          }
        });
    }
  }

  /**
   * loginSubmit
   * Sends a request for an auth token with supplied information from the user
   */
  loginSubmit() {
    // Resolves an error where refreshed user might have gotten an error due to unwanted data retention
    if (this.userData.oauthClientSecret !== 'secret') {
      this.userData.oauthClientSecret = 'secret';
      localStorage.setItem('UserData', JSON.stringify(this.userData));
    }
    this.shake = false; // Resets the shake animation

    // Sends login info to the server

    // Constructs the parameters that will be sendt to the server
    let body = new HttpParams();
    body = body.set('grant_type', 'password');
    body = body.append('username', this.loginUser.email.toString());
    body = body.append('password', this.loginUser.password.toString());
    body = body.append('client_id', this.userData.oauthClientId);

    this.http.post(this.userData._links['login OAuth2'].href, body, {
      // Constructs the headers
      headers: new HttpHeaders({
        Authorization: 'Basic ' + btoa(this.userData.oauthClientId + ':' + this.userData.oauthClientSecret),
        'Content-type': 'application/x-www-form-urlencoded; charset=utf-8'
      })
    }).subscribe(
      result => {
        // @ts-ignore
        this.userData.oauthClientSecret = result.access_token;
        this.getFurtherApiDetails();
      }, error => {
        if (error.error.error_description === 'Bad credentials') {
          this.shake = true; // Shakes the main card to illustrate that there was en error
          // @ts-ignore
          this.snackBar.open(this.translate.get('ErrorsAndWarns.Wrong-Password').value, this.translate.get('ErrorsAndWarns.Close').value);
        } else {
          // @ts-ignore
          alert(this.translate.get('ErrorsAndWarns.Unexpected').value);
          console.error(error);
        }
      }
    );
  }

  /**
   * getFurtherApiDetails
   * Fetches API details requried after the user has signed in this is then used in the menu.component
   */
  getFurtherApiDetails() {
    this.http.get<IAPIResponse>(startUrl, {
      headers: new HttpHeaders({
        Authorization: 'Bearer ' + this.userData.oauthClientSecret
      })
    }).subscribe(result => {
      this.userData.userName = this.loginUser.email;
      this.userData._links = result._links;
      this.userData._links['login OAuth2'].href = this.userData._links['login OAuth2'].href.split('?')[0];
      this.userData.nav = 'Menu';
      localStorage.setItem('UserData', JSON.stringify(this.userData));
      this.router.navigate(['/Menu']);
    }, error => {
      console.error(error);
    });
  }

  /**
   * changeMode
   * Swithces between
   */
  public changeMode() {
    this.login = !this.login;
  }

  /**
   * changeLang
   * Changes the current language to the inputed via the lang parameter
   * @param lang The language to change into
   */
  changeLang(lang: string) {
    this.translate.use(lang);
    this.userData.defaultLang = lang;
    localStorage.setItem('UserData', JSON.stringify(this.userData));
  }
}

@Component({
  selector: 'GDPR.Content',
  templateUrl: './GDPRContent.html',
})
export class GDPRContent {
  constructor(public dialogRef: MatDialogRef<GDPRContent>) {
  }

  /**
   * Is called once the dialog closes in this instance it will just close the dialog
   */
  OnNoClick() {
    this.dialogRef.close();
  }

}

/**
 * INewUser
 * Contains two strings, the password and the username(email)
 */
interface INewUser {
  username: string;
  password: string;
}

/**
 * IAPIResponse
 * AN interface that represents what the client expects when it asks for API-details
 */
interface IAPIResponse {
  _links: Links;
}
