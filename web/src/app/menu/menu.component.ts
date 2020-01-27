import { Component, OnInit } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Router} from '@angular/router';
import {UserData} from '../models/UserData.model';
import {Link} from '../models/link.model';
import {REL_PROJECT} from '../common';
import {Project} from '../models/Project.model';

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
  private projects: Project[];
  public title = 'Grouse';

  constructor(http: HttpClient, router: Router) {
    this.http = http;
    this.router = router;
    this.userData = new UserData();
  }

  ngOnInit() {
    this.userData = JSON.parse(localStorage.getItem('UserData'));
    this.getUserData();
  }

  getUserData() {
    this.http.get(this.userData.userAdress + '/' + this.userData.userName, {
      headers: new HttpHeaders({
          Authorization: 'Bearer ' + this.userData.oauthClientSecret
        }
      )
    }).subscribe(result => {
      // @ts-ignore
      this.userData.links = result.links;
      this.getActiveProjects();
    }, error => {
      console.error(error);
      this.logout();
    });
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

  getActiveProjects() {
    for (const link of this.userData.links) {
      if (link.rel === REL_PROJECT) {
        this.http.get(link.href, {
          headers: new HttpHeaders({
            Authorization: 'Bearer ' + this.userData.oauthClientSecret
          })
        }).subscribe(result => {
          // @ts-ignore
          this.projects = result;
        }, error => {
          console.error(error);
        });
      }
    }
  }
}
