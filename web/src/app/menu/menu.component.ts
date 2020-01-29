import {Component, Inject, OnInit} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Router} from '@angular/router';
import {UserData} from '../models/UserData.model';
import {Link} from '../models/link.model';
import {REL_PROJECT} from '../common';
import {Project} from '../models/Project.model';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";

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

  constructor(http: HttpClient, router: Router, dialogBox: MatDialog) {
    this.http = http;
    this.router = router;
    this.userData = new UserData();
    this.dialogBox = dialogBox;
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
      for (const link of this.userData.links) {
        if (link.rel === REL_PROJECT) {
          this.projectsLink = link.href;
        }
      }
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
      // @ts-ignore
      this.projects = result;
    }, error => {
      console.error(error);
    });
  }

  newProject(){
    let ProjectName: string;
    let OrgName: string;

    const dialogRef = this.dialogBox.open(NewProjectDialog, {
      width: '300px',
      data: {Name: ProjectName, Org: OrgName}
    });

    dialogRef.afterClosed().subscribe(result => {
      // Result is now what the user entered in the dialog
      ProjectName = result.Name;
      OrgName = result.Org

      this.http.post(this.projectsLink, {projectName: ProjectName, organisationName: OrgName}, {
        headers: new HttpHeaders({
          Authorization: 'Bearer ' + this.userData.oauthClientSecret
        })
      }).subscribe(result => {
        this.getActiveProjects();
      }, error => {
        console.error(error);
      });
    })
  }
  openProject(project){
    this.userData.currentProject = project;
    this.userData.nav = 'kravEdit';
    localStorage.setItem('UserData', JSON.stringify(this.userData));
    this.router.navigate(['/kravEdit']);
  }
}

export interface INewProject {
  Name: string;
  Org: string;
}

@Component({
  selector: 'NewProject.Dialog',
  templateUrl: '../Modals/NewProject.Dialog.html'
})
export class NewProjectDialog {
  constructor(public dialogRef: MatDialogRef<NewProjectDialog>, @Inject(MAT_DIALOG_DATA) public data: INewProject){}

  onNoClick(){
    this.dialogRef.close();
  }
}
