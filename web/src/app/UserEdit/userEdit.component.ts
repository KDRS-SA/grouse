import {Component, OnInit} from '@angular/core';
import {UserData} from '../models/UserData.model';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Router} from '@angular/router';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {MatDialog} from "@angular/material/dialog";
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-root',
  templateUrl: './userEdit.component.html',
  styleUrls: [
    './userEdit.component.css',
    '../common.css'
  ]
})

// tslint:disable-next-line:class-name
export class userEditComponent implements  OnInit {
  private userData: UserData;
  private http: HttpClient;
  private router: Router;
  private emailForm: FormGroup;
  private passwordForm: FormGroup;
  private formBuilder: FormBuilder;
  private newEmail: string;
  private newPassword: string;
  private oldPassword: string;
  private repeatPassword: string;

  constructor(http: HttpClient, router: Router, formBuilder: FormBuilder, public translate: TranslateService) {
    this.http = http;
    this.router = router;
    this.formBuilder = formBuilder;
    translate.addLangs(['no', 'en', 'ny']);
    translate.setDefaultLang('no');
  }

  ngOnInit(): void {
    this.userData = JSON.parse(localStorage.getItem('UserData'));
    console.log(this.userData);

    // Forms
    this.emailForm = this.formBuilder.group({
      email: [this.newEmail, [
        Validators.email,
        Validators.required
      ]]
    });

    this.passwordForm = this.formBuilder.group({
      oldP: [ this.oldPassword, [
        Validators.required
      ]],
      newP: [ this.newPassword, [
        Validators.required
      ]],
      repP: [ this.repeatPassword, [
        Validators.required
      ]]
    });
  }

  logout() {
    localStorage.clear();
    this.http.get(this.userData._links['logout OAuth2'].href, {
      headers: new HttpHeaders({
        Authorization: 'Bearer ' + this.userData.oauthClientSecret
      })
    }).subscribe(result => {
    }, error => {
      console.log(error);
    });
    this.router.navigate(['/']);
    location.reload();
  }

  goToMainMenu() {
    this.userData.nav = 'Menu';
    localStorage.setItem('UserData', JSON.stringify(this.userData));
    this.router.navigate(['/Menu']);
  }

  newEmailSubmit() {
    console.log(this.newEmail);
  }

  newPasswordSubmit() {
    console.log(this.newPassword);
  }
}
