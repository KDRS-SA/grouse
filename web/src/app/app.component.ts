import {Component, Inject, OnInit} from '@angular/core';
import {Animations} from './app.animations';
import {FormGroup, FormBuilder, FormControl, Validators} from '@angular/forms';
import {HttpClient, HttpHandler} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {RegisterModel} from "./models/register.model";
import {LoginModel} from "./models/login.model";
import {FormControl, Validators} from '@angular/forms';
import {HttpClient, HttpHandler, HttpResponse} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {REL_LOGIN_OAUTH, REL_LOGOUT_OAUTH, REL_USER, startUrl} from './common';

// Lar klienten konfigurere HttpClienten, som brukes for å komunisere med serveren
@Injectable()
export class ConfigService {

    constructor(private http: HttpClient) {}
}

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  animations: [
    Animations.toggleSlide
  ]
})



export class AppComponent implements  OnInit{
  public title = 'Grouse';
  public login: boolean;
  
  regUser: RegisterModel = new RegisterModel();
  loginUser: LoginModel = new LoginModel();
  registerForm: FormGroup;
  loginForm: FormGroup;

  public loginAdress = '';
  public logoutAdress = '';
  public userAdress = '';
  public oauthClientId = 'grouse-client';
  public oauthClientSecret = 'secret';

  private http: HttpClient;

  email = new FormControl('',
    [Validators.required, Validators.email]);

  getErrorMessage() {
    return this.email.hasError('required') ? 'Du må skrive inn en E-post adresse' :
    this.email.hasError('email') ? 'Ikke en gyldig E-post Adresse' : '';
  }

  constructor(http: HttpClient, private formBuilder: FormBuilder) {
    this.login = true;
    this.http = http;
    this.getApiDetails();
  }

  ngOnInit() {
    this.registerForm = this.formBuilder.group({
      'email': [this.regUser.email, [
        Validators.required,
        Validators.email
      ]],
      'name': [this.regUser.name, [
        Validators.required
      ]],
      'password': [this.regUser.password, [
        Validators.required,
        Validators.minLength(5),
        Validators.maxLength(30)
      ]],
      'passwordRepeat': [this.regUser.passwordRepeat, [
        Validators.required,
        Validators.minLength(5),
        Validators.maxLength(30)
      ]]
    });

    this.loginForm = this.formBuilder.group({
      'email': [this.loginUser.email, [
        Validators.required,
        Validators.email
      ]],
      'password': [this.loginUser.password, [
        Validators.required,
        Validators.minLength(5),
        Validators.maxLength(30)
      ]]
    });
  }

  registerSubmit(){
    alert("E-post: " + this.regUser.email + "+n" + "Navn: " + this.regUser.name + "\n" + "Passord: " + this.regUser.password + " og " + this.regUser.passwordRepeat);
  }

  loginSubmit(){
    alert("E-post: " + this.loginUser.email + "\n" + "Passord: " + this.loginUser.password);

  }


  public changeMode() {
    this.login = !this.login;
  }

  // Fetches ApiDetails from the server, that is utialized for further communication
  public getApiDetails() {
    this.http.get<IApiFetchResponse>(startUrl).subscribe(result => {
      // tslint:disable-next-line:prefer-for-of
      for (let i = 0; i < result.apiDetails.length; i++) {
        const rel = result.apiDetails[i].rel;
        if (rel === REL_LOGIN_OAUTH) {
          this.loginAdress = result.apiDetails[i].href.split(/(\?)/)[0];
          console.log('loginAddress  is set to [' + this.loginAdress + ']');
        }
        if (rel === REL_USER) {
          this.userAdress = result.apiDetails[i].href;
          console.log('userAddress  is set to [' + this.userAdress + ']');
        }
        if (rel === REL_LOGOUT_OAUTH) {
          this.logoutAdress = result.apiDetails[i].href;
          console.log('logoutAddress   is set to [' + this.logoutAdress + ']');
        }
      }
    }, error => console.error(error));
  }
}

// Interfaces for Response messages that deals with login
interface IApiFetchResponse {
  apiDetails: IapiDetails[];
  Links: string[];
}

interface IapiDetails {
  href: string;
  rel: string;
  templated: boolean;
}
