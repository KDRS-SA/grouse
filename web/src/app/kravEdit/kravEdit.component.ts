import {Component, OnInit} from "@angular/core";
import {Router} from '@angular/router';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {UserData} from '../models/UserData.model';
import {REL_FUNCTIONALITY, REL_PROJECT} from "../common";
import {projectFunctionality} from "../models/projectFunctionality.model";

@Component({
  selector: 'app-root',
  templateUrl: './kravEdit.component.html',
  styleUrls: [
    './kravEdit.component.css',
    '../common.css'
  ]
})

export class kravEditComponent implements OnInit{

  public title = 'Grouse';

  private http: HttpClient;
  private router: Router;
  private userData: UserData;
  private projectLink: string;
  private mainData: projectFunctionality[];

  constructor(http: HttpClient, router: Router) {
    this.http = http;
    this.router = router;
    this.projectLink = '';
  }

  ngOnInit(){
    this.userData = JSON.parse(localStorage.getItem('UserData'));
    for (const link of this.userData.currentProject.links){
      if (link.rel === REL_FUNCTIONALITY){
        this.projectLink = link.href;
      }
    }
    console.log(this.projectLink);
    this.http.get(this.projectLink, {
      headers: new HttpHeaders({
          Authorization: 'Bearer ' + this.userData.oauthClientSecret
        }
      )
    }).subscribe(result => {
      // @ts-ignore
      this.mainData = result;
      console.log(this.mainData);
    }, error => {
      console.error(error);
    });
  }

  toMainMenu(){
    this.userData.nav = 'Menu';
    localStorage.setItem('UserData', JSON.stringify(this.userData));
    this.router.navigate(['/Menu']);
  }

  logout(){
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
}


