import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {REL_LOGIN_OAUTH, REL_LOGOUT_OAUTH, REL_USER, startUrl} from './common';
import {HttpClient} from '@angular/common/http';
import {UserData} from './models/UserData.model';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./common.css']
})
export class AppComponent implements OnInit {
  private router: Router;
  private http: HttpClient;
  private userData: UserData;

  public loading: boolean;

  constructor(router: Router, http: HttpClient) {
    this.router = router;
    this.loading = true;
    this.http = http;
    this.userData = new UserData();
  }


  ngOnInit() {
    // Uses allready existing variables if the user refreshed the page
    if (localStorage.getItem('UserData') != null) {
      this.userData = JSON.parse(localStorage.getItem('UserData'));
    }
    // Runs the function beneath that fetches API info from the server
    this.getApiDetails();
  }

  // Fetches ApiDetails from the server, that is utialized for further communication
  public getApiDetails() {
    this.http.get<IApiFetchResponse>(startUrl).subscribe(result => {
      // tslint:disable-next-line:prefer-for-of
      for (let i = 0; i < result.apiDetails.length; i++) {
        const rel = result.apiDetails[i].rel;
        if (rel === REL_LOGIN_OAUTH) {
          this.userData.loginAdress = result.apiDetails[i].href.split(/(\?)/)[0];
          console.log('loginAddress  is set to [' + this.userData.loginAdress + ']');
        }
        if (rel === REL_USER) {
          this.userData.userAdress = result.apiDetails[i].href;
          console.log('userAddress  is set to [' + this.userData.userAdress + ']');
        }
        if (rel === REL_LOGOUT_OAUTH) {
          this.userData.logoutAdress = result.apiDetails[i].href;
          console.log('logoutAddress   is set to [' + this.userData.logoutAdress + ']');
        }
      }
      localStorage.setItem('UserData', JSON.stringify(this.userData));
      this.loading = false;
      this.navigate();
    }, error => console.error(error));
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
  apiDetails: IapiDetails[];
  Links: string[];
}

interface IapiDetails {
  href: string;
  rel: string;
  templated: boolean;
}
