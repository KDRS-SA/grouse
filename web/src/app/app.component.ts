import {Component, Inject, OnInit} from '@angular/core';
import {Animations} from './app.animations';
import {FormGroup, FormBuilder, FormControl, Validators} from '@angular/forms';
import {HttpClient, HttpHandler} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {RegisterModel} from "./models/register.model";
import {LoginModel} from "./models/login.model";

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

  public httpGetTest() {
    this.http.get<string>('http://localhost:9294/grouse/').subscribe(result => {
      console.log(result);
    }, error => console.error(error));
  }
}

