import {Component} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Router} from "@angular/router";
import {UserData} from "../models/UserData.model";
import {Project} from "../models/Project.model";
import {MatDialog} from "@angular/material/dialog";
import {TranslateService} from "@ngx-translate/core";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {User} from "../models/User";
import {startUrl} from "../common";

@Component({
  selector: 'app-root',
  templateUrl: './Admin.component.html',
  styleUrls: [
    './Admin.component.css',
    '../common.css'
  ]
})

export class adminComponent {
  private http: HttpClient;
  private router: Router;
  private userData: UserData;
  private dialogBox: MatDialog;

  private searchUser: string;
  private searchProject: string;

  private listOfUsers: User[];
  private listOfProjects: Project[];
  private usermode: boolean;
  private selectedUser: User;
  private selectedProject: Project;

  constructor(http: HttpClient, router: Router, dialogBox: MatDialog, public translate: TranslateService) {
    this.http = http;
    this.router = router;
    this.dialogBox = dialogBox;
    this.translate = translate;
    translate.addLangs(['Bokmål', 'English', 'Nynorsk']);
    translate.setDefaultLang('Bokmål');
    this.searchProject = "";
    this.searchUser = "";
    this.listOfProjects = [];
    this.listOfUsers = [];
    this.usermode = true;
    this.getUserData();
  }

  /**
   * getUserData
   * Fetches data related tot the current user
   */
  getUserData() {
    this.userData = JSON.parse(localStorage.getItem('UserData'));
    this.translate.setDefaultLang(this.userData.defaultLang);
    this.getListOfUsers();
    this.getListOfProjects();
  }

  getListOfUsers() {
    this.http.get(this.userData._links["create-user"].href, {
      headers: new HttpHeaders({
        Authorization: 'Bearer ' + this.userData.oauthClientSecret
      })
    }).subscribe(result => {
      if (this.searchUser !== "") {
        // @ts-ignore
        this.listOfUsers = result._embedded.users.filter(user => user.username.includes(this.searchUser));
      } else {
        // @ts-ignore
        this.listOfUsers = result._embedded.users;
      }
      console.log(this.listOfUsers);
    }, error => {
      console.error(error);
    });
  }

  selectThisUser(username: string) {
    this.usermode = true;
    this.selectedUser = this.listOfUsers.find(user => user.username === username);
  }

  selectThisProject(id: string) {
    this.usermode = false;
    this.selectedProject = this.listOfProjects.find(project => project.projectId === id);
  }

  getListOfProjects() {
    this.http.get(this.userData._links["project-list-all"].href, {
      headers: new HttpHeaders({
        Authorization: 'Bearer ' + this.userData.oauthClientSecret
      })
    }).subscribe(result => {
      if (this.searchProject !== "") {
        // @ts-ignore
        this.listOfProjects = result._embedded.projects.filter(project => project.projectName.includes(this.searchUser));
      } else {
        // @ts-ignore
        this.listOfProjects = result._embedded.projects;
      }
      console.log(this.listOfProjects);
    }, error => {
      console.error(error);
    });
  }

  /**
   * logout
   *
   * This method both sends a call to the server to invalidate the current auth-token and sends the user to the login page
   * As this method is duplicated it will proabobly be moved later
   */
  logout() {
    localStorage.clear();
    this.http.get(this.userData._links['logout OAuth2'].href, {
      headers: new HttpHeaders({
        Authorization: 'Bearer ' + this.userData.oauthClientSecret
      })
    }).subscribe(result => {
      // tslint:disable-next-line:no-shadowed-variable
    }, error => {
      console.log(error);
    });
    this.router.navigate(['/']);
    location.reload();
  }

  /**
   * goToMainMenu
   *
   * This method sends the user to the main menu when called
   */
  goToMainMenu() {
    this.userData.nav = 'Menu';
    localStorage.setItem('UserData', JSON.stringify(this.userData));
    this.router.navigate(['/Menu']);
  }

  /**
   * enterUserEdit()
   * Opens the user edit page
   */
  enterUserEdit() {
    this.userData.nav = 'userEdit';
    localStorage.setItem('UserData', JSON.stringify(this.userData));
    this.router.navigate(['/userEdit']);
  }
}
