import {Component, Inject} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Router} from "@angular/router";
import {UserData} from "../models/UserData.model";
import {Project} from "../models/Project.model";
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";
import {TranslateService} from "@ngx-translate/core";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {User} from "../models/User";
import {startUrl} from "../common";
import {DeleteProjectDialog} from "../Menu/menu.component";

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
  private usersOfThisProject: User[];

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

  /**
   * getListOfUsers
   * Fetches list from server of all users
   */
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
    }, error => {
      console.error(error);
    });
  }

  /**
   * selectThisUserName
   * Selects a user with given username
   * @param inn The username of the user to switch to
   */
  selectThisUserName(inn: string) {
    this.selectThisUser(this.listOfUsers.find(user => user.username === inn));
  }

  /**
   * selectThisUser
   * Selects a given user
   * @param inn the user to select
   */
  selectThisUser(inn: User) {
    this.usermode = true;
    this.selectedUser = inn;
  }

  /**
   * selectThisProject
   * Selects a given project
   * @param inn the project to select
   */
  selectThisProject(inn: Project) {
    this.usermode = false;
    this.selectedProject = inn;
    this.getUsersAssociatedWithProject(inn);
  }

  /**
   * getListOfProjects
   * Fetches a list of all projects from the server
   */
  getListOfProjects() {
    this.http.get(this.userData._links["project-list-all"].href, {
      headers: new HttpHeaders({
        Authorization: 'Bearer ' + this.userData.oauthClientSecret
      })
    }).subscribe(result => {
      if (this.searchProject !== "") {
        // @ts-ignore
        this.listOfProjects = result._embedded.projects.filter(project => project.projectName.includes(this.searchProject));
      } else {
        // @ts-ignore
        this.listOfProjects = result._embedded.projects;
      }
    }, error => {
      console.error(error);
    });
  }

  /**
   * filterProjectsByUser
   * Returns a list of projects filtered by the current selected user
   */
  filterProjectsByUser(): Project[] {
    return this.listOfProjects.filter(project => project.ownedBy === this.selectedUser.username);
  }

  /**
   * getUsersAssociatedWithProject
   * Fetches users that are associated with the current project including shares
   * @param project wich project to find users of
   */
  getUsersAssociatedWithProject(project: Project){
    // Resets the the array of users so that a loading spinner can be displayed
    this.usersOfThisProject = null;
    this.http.get(project._links.access.href, {
      headers: new HttpHeaders({
        Authorization: 'Bearer ' + this.userData.oauthClientSecret
      })
    }).subscribe(result => {
      // @ts-ignore
      this.usersOfThisProject = result._embedded.users;
      // tslint:disable-next-line:no-shadowed-variable
    }, error => {
      console.error(error);
    });
  }

  /**
   * deleteProject
   * Opens a dialog pulled from the main menu (is pulled from menu.component.ts) as a deletion prompt for deleting a project
   * didnt need remaking
   *
   * @param inn Which project to delete
   */
  deleteProject() {
    const dialogRef = this.dialogBox.open(DeleteProjectDialog, {
      width: '300px'
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.http.delete(
          this.selectedProject._links.self.href,
          {
            headers: new HttpHeaders({
              Authorization: 'Bearer ' + this.userData.oauthClientSecret
            })
          }).subscribe(
          // tslint:disable-next-line:no-shadowed-variable
          result => {
            this.getListOfProjects();
          }, error => console.error(error));
      }});
  }

  /**
   * revokeShare
   * Removes a sharing link for the specified user on the currently selected project
   *
   * @param user Which user to remove the share for
   */
  revokeShare(user: User){
    this.http.delete(this.selectedProject._links.share.href.replace('user_email_address', user.username), {
      headers: new HttpHeaders({
        Authorization: 'Bearer ' + this.userData.oauthClientSecret
      })
    }).subscribe(result => {
      this.getUsersAssociatedWithProject(this.selectedProject);
    }, error => {
      console.error(error);
    });
  }

  /**
   * deleteUser
   * Deletes the currently selected user
   */
  deleteUser() {
    const dialogRef = this.dialogBox.open(AdminDeleteUserDialog, {
      width: '300px'
    })

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.http.delete(this.selectedUser._links.self.href, {
          headers: new HttpHeaders({
            Authorization: 'Bearer ' + this.userData.oauthClientSecret
          })
        }).subscribe(result => {
          this.getListOfUsers();
        }, error => {
          console.error(error);
        });
      }
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

@Component({
  // tslint:disable-next-line:component-selector
  selector: 'AdminDeleteUser.Dialog',
  templateUrl: '../Modals/AdminDeleteUser.Dialog.html',
  styleUrls: [
    './Admin.component.css',
    '../common.css'
  ]
})
export class AdminDeleteUserDialog {
  constructor(public dialogRef: MatDialogRef<AdminDeleteUserDialog>, @Inject(MAT_DIALOG_DATA) public data: boolean) {
    this.data = true;
  }

  onNoClick() {
    this.dialogRef.close();
  }
}
