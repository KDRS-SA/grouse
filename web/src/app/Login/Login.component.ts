import {Component, OnInit} from '@angular/core';
import {Animations} from '../app.animations';
import {FormGroup, FormBuilder, FormControl, Validators} from '@angular/forms';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {RegisterModel} from '../models/register.model';
import {LoginModel} from '../models/login.model';
import {Router} from '@angular/router';
import {UserData} from '../models/UserData.model';
import {Data} from '../data.service';

@Component({
  selector: 'app-root',
  templateUrl: './Login.component.html',
  styleUrls: [
    './Login.component.css',
    '../common.css'
  ],
  animations: [
    Animations.toggleSlide
  ]
})

export class LoginComponent implements  OnInit {
  public title = 'Grouse';
  public login: boolean;

  regUser: RegisterModel = new RegisterModel();
  loginUser: LoginModel = new LoginModel();
  registerForm: FormGroup;
  loginForm: FormGroup;

  private http: HttpClient;
  private router: Router;
  private userData: UserData;
  private data: Data;

  email = new FormControl('',
    [Validators.required, Validators.email]);

  getErrorMessage() {
    return this.email.hasError('required') ? 'Du må skrive inn en E-post adresse' :
    this.email.hasError('email') ? 'Ikke en gyldig E-post Adresse' : '';
  }

  constructor(http: HttpClient, private formBuilder: FormBuilder, router: Router, data: Data) {
    this.login = true;
    this.http = http;
    this.router = router;
    this.data = data;
    this.userData = new UserData();
  }

  ngOnInit() {
    this.registerForm = this.formBuilder.group({
      email: [this.regUser.email, [
        Validators.required,
        Validators.email
      ]],
      name: [this.regUser.name, [
        Validators.required
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

    this.data.currentMessage.subscribe(msg => this.userData = msg);
  }

  registerSubmit() {
    // tslint:disable-next-line:max-line-length
    alert('E-post: ' + this.regUser.email + '+n' + 'Navn: ' + this.regUser.name + '\n' + 'Passord: ' + this.regUser.password + ' og ' + this.regUser.passwordRepeat);
  }

  loginSubmit() {
    console.log('Loggin request called with username: ' + this.loginUser.email + ', on adress: ' + this.userData.loginAdress);

    // Sends login info to the server

    // Constructs the parameters that will be sendt to the server
    let body = new HttpParams();
    body = body.set('grant_type', 'password');
    body = body.append('username', this.loginUser.email.toString());
    body = body.append('password', this.loginUser.password.toString());
    body = body.append('client_id', this.userData.oauthClientId);

    this.http.post(this.userData.loginAdress, body, {
      // Constructs the headers
      headers: new HttpHeaders({
        Authorization: 'Basic ' + btoa(this.userData.oauthClientId + ':' + this.userData.oauthClientSecret),
        'Content-type': 'application/x-www-form-urlencoded; charset=utf-8'
      })
    }).subscribe(
      result => {
        console.log(result);
        this.router.navigate(['/Menu']);
      }, error => {
        if (error.error.error_description === 'Bad credentials') {
          alert('Passord eller epost er galt');
        } else {
          alert('En uventet feil har oppstått, prøv igjen. Eller se konsol, for flere detaljer');
          console.log(error);
        }
      }
    );
  }

  public changeMode() {
    this.login = !this.login;
  }
}
