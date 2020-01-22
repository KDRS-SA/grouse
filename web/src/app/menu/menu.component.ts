import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";
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
  public title ='Grouse';

  constructor(http: HttpClient, router: Router) {
    this.http = http;
    this.router = router;
    this.userData = new UserData();
  }

  ngOnInit() {
    this.userData = JSON.parse(localStorage.getItem('UserData'));
  }
}
