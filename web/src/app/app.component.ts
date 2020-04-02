import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {REL_LOGIN_OAUTH, REL_LOGOUT_OAUTH, REL_USER, startUrl} from './common';
import {HttpClient} from '@angular/common/http';
import {UserData} from './models/UserData.model';
import {Link} from './models/link.model';
import {MatSnackBar} from '@angular/material/snack-bar';
import {Links} from './models/links.model';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./common.css']
})
export class AppComponent implements OnInit {
  private router: Router;
  private http: HttpClient;
  private userData: UserData;
  private snackBar: MatSnackBar;

  public loading: boolean;

  constructor(router: Router, http: HttpClient, snackBar: MatSnackBar) {
    this.router = router;
    this.loading = true;
    this.http = http;
    this.userData = new UserData();
    this.userData._links = new Links();
    this.snackBar = snackBar;
  }


  ngOnInit() {
    // Uses allready existing variables if the user refreshed the page
    if (localStorage.getItem('UserData') !== null) {
      this.userData = JSON.parse(localStorage.getItem('UserData'));
    }
    // Runs the function beneath that fetches API info from the server
    this.getApiDetails();
  }

  // Fetches ApiDetails from the server, that is utialized for further communication
  public getApiDetails() {
    this.http.get<IApiFetchResponse>(startUrl).subscribe(result => {
      this.userData._links['login OAuth2'] = new Link();
      this.userData._links['login OAuth2'].href = result._links['login OAuth2'].href.split('?')[0];
      this.userData._links['create-user'] = result._links['create-user'];
      localStorage.setItem('UserData', JSON.stringify(this.userData));
      this.loading = false;
      this.navigate();
    }, error => {
        console.error(error);
        const ref = this.snackBar.open('Could not reach the Grouse server', 'Try again');
        ref.onAction().subscribe(() => {
          this.getApiDetails();
          this.snackBar.dismiss();
        });
    });
  }

  // Navigates the user to the componenet they were inn if the page is refreshed
  navigate() {
    if (this.userData.nav === '' || this.userData.nav === null) {
      this.router.navigate(['/Login']);
    } else {
      this.router.navigate(['/' + this.userData.nav]);
    }
  }
}

// Interfaces for Response messages that deals with login
interface IApiFetchResponse {
  _links: Links;
}

interface IapiDetails {
  href: string;
  rel: string;
  templated: boolean;
}
