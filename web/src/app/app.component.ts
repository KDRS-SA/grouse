import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {REL_LOGIN_OAUTH, REL_LOGOUT_OAUTH, REL_USER, startUrl} from './common';
import {HttpClient} from '@angular/common/http';
import {Data} from './data.service';
import {UserData} from './models/UserData.model';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./common.css']
})
export class AppComponent implements OnInit {
  private router: Router;
  private http: HttpClient;
  private data: Data;
  private userData: UserData;

  public loading: boolean;

  constructor(router: Router, http: HttpClient, data: Data) {
    this.router = router;
    this.loading = true;
    this.http = http;
    this.data = data;
    this.userData = new UserData();
  }


  ngOnInit() {
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
      this.data.changeMessage(this.userData);
      this.loading = false;
      this.router.navigate(['/Login']);
    }, error => console.error(error));
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
