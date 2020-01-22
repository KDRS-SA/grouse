import { Component, OnInit } from '@angular/core';
import {REL_LOGIN_OAUTH, REL_LOGOUT_OAUTH, REL_USER, startUrl} from '../common';
import {Data} from '../data.service';
import {HttpClient} from "@angular/common/http";
import {FormBuilder} from "@angular/forms";
import {Router} from "@angular/router";
import {UserData} from "../models/UserData.model";

@Component({
  selector: 'app-root',
  templateUrl: './menu.component.html',
  styleUrls: [
    './menu.component.css',
    '../common.css'
  ]
})
export class MenuComponent implements OnInit {
  private http: HttpClient;
  private router: Router;
  private userData: UserData;
  private data: Data;
  public title ='Grouse';

  constructor(http: HttpClient, router: Router, data: Data) {
    this.http = http;
    this.router = router;
    this.data = data;
    this.userData = new UserData();
  }

  ngOnInit() {
    this.data.currentMessage.subscribe(user => this.userData = user);
  }
}
