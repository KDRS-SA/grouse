import {Component, Inject, OnInit} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Router} from '@angular/router';
import {UserData} from '../models/UserData.model';
import {Link} from '../models/link.model';
import {convertFromLegacy, REL_PROJECT} from '../common';
import {Project} from '../models/Project.model';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material/dialog';
import {TranslateService} from "@ngx-translate/core";

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
  private projectsLink: string;
  private dialogBox: MatDialog;

  public title = 'Grouse';

  constructor(http: HttpClient, router: Router, dialogBox: MatDialog, public translate: TranslateService) {
    this.http = http;
    this.router = router;
    this.userData = new UserData();
    this.dialogBox = dialogBox;
    translate.addLangs(['no', 'en', 'ny']);
    translate.setDefaultLang('no');
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
      this.userData._links = result._links;
      this.projectsLink = this.userData._links.prosjekt.href;
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
    this.http.get(this.projectsLink, {
      headers: new HttpHeaders({
        Authorization: 'Bearer ' + this.userData.oauthClientSecret
      })
    }).subscribe(result => {
      const temp = result;
      // @ts-ignore
      for (const proj of temp) {
        proj._links = convertFromLegacy(proj.links);
        proj.links = null;
      }
      // @ts-ignore
      this.projects = temp;
    }, error => {
      console.error(error);
    });
  }

  newProject() {
    let ProjectName: string;
    let OrgName: string;

    const dialogRef = this.dialogBox.open(NewProjectDialog, {
      width: '300px',
      data: {Name: ProjectName, Org: OrgName}
    });

    dialogRef.afterClosed().subscribe(result => {
      // Result is now what the user entered in the dialog
      ProjectName = result.Name;
      OrgName = result.Org;

      this.http.post(this.projectsLink, {projectName: ProjectName, organisationName: OrgName}, {
        headers: new HttpHeaders({
          Authorization: 'Bearer ' + this.userData.oauthClientSecret
        })
        // tslint:disable-next-line:no-shadowed-variable
      }).subscribe(result => {
        this.getActiveProjects();
      }, error => {
        console.error(error);
      });
    });
  }

  openProject(project) {
    this.userData.currentProject = project;
    this.userData.nav = 'kravEdit';
    localStorage.setItem('UserData', JSON.stringify(this.userData));
    this.router.navigate(['/kravEdit']);
  }

  enterUserEdit() {
    this.userData.nav = 'userEdit';
    localStorage.setItem('UserData', JSON.stringify(this.userData));
    this.router.navigate(['/userEdit']);
  }
}

export interface INewProject {
  Name: string;
  Org: string;
}

@Component({
  // tslint:disable-next-line:component-selector
  selector: 'NewProject.Dialog',
  templateUrl: '../Modals/NewProject.Dialog.html'
})
// tslint:disable-next-line:component-class-suffix
export class NewProjectDialog {
  constructor(public dialogRef: MatDialogRef<NewProjectDialog>, @Inject(MAT_DIALOG_DATA) public data: INewProject) {}

  onNoClick() {
    this.dialogRef.close();
  }
}