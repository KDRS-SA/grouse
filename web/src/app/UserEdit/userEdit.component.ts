import {Component, OnInit} from '@angular/core';
import {Animations} from '../app.animations';
import {UserData} from '../models/UserData.model';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Router} from '@angular/router';

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

  constructor(http: HttpClient, router: Router) {
    this.http = http;
    this.router = router;
  }

  ngOnInit(): void {
    this.userData = JSON.parse(localStorage.getItem('UserData'));
    console.log(this.userData);
  }

  logout() {
    localStorage.clear();
    this.http.get(this.userData.logoutAdress, {
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
}
