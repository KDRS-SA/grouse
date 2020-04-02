import {Component, Inject, OnInit} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Router} from '@angular/router';
import {UserData} from '../models/UserData.model';
import {Project} from '../models/Project.model';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material/dialog';
import {TranslateService} from '@ngx-translate/core';
import {Template} from '../models/template';
import {Animations} from '../app.animations';

@Component({
  selector: 'app-root',
  templateUrl: './menu.component.html',
  styleUrls: [
    './menu.component.css',
    '../common.css'
  ],
  animations: [
    Animations.shake
  ]
})
export class MenuComponent implements OnInit {
  private http: HttpClient;
  private router: Router;
  private userData: UserData;
  private projects: Project[];
  private dialogBox: MatDialog;
  private deleteMode: boolean;
  private shaking: number;

  public title = 'Grouse';

  constructor(http: HttpClient, router: Router, dialogBox: MatDialog, public translate: TranslateService) {
    this.http = http;
    this.router = router;
    this.userData = new UserData();
    this.dialogBox = dialogBox;
    translate.addLangs(['Bokmål', 'English', 'Nynorsk']);
    translate.setDefaultLang('Bokmål');
    this.shaking = 0;
    this.deleteMode = false;
  }

  ngOnInit() {
    this.getUserData();
  }

  /**
   * getUserData
   * Fetches data related tot the current user
   */
  getUserData() {
    this.userData = JSON.parse(localStorage.getItem('UserData'));
    console.log(this.userData);
    this.getActiveProjects();
  }

  /**
   * logout
   * Ends the current session and sends the user back to the loginpage
   */
  logout() {
    localStorage.clear();
    this.http.get(this.userData._links['logout OAuth2'].href, {
      headers: new HttpHeaders({
        Authorization: 'Bearer ' + this.userData.oauthClientSecret
      })
    }).subscribe(result => {
    }, error => {
      this.router.navigate(['/']);
      location.reload();
      console.log(error);
    });
    this.router.navigate(['/']);
    location.reload();
  }

  /**
   * getActiveProjects
   * fetches active projects for the current user, includes projects that are shared with the current user
   */
  getActiveProjects() {
    this.http.get(this.userData._links['project-list'].href, {
      headers: new HttpHeaders({
        Authorization: 'Bearer ' + this.userData.oauthClientSecret
      })
    }).subscribe(result => {
      console.log(result);
      // @ts-ignore
      if (result._embedded !== undefined) {
        // @ts-ignore
        this.projects = result._embedded.projects;
      }
    }, error => {
      this.logout();
    });
  }

  /**
   * newProject
   * Creates a new project, opens a modal where the info is entered
   */
  newProject() {
    this.http.get(this.userData._links['template-list'].href, {
      headers: new HttpHeaders({
        Authorization: 'Bearer ' + this.userData.oauthClientSecret
      })
    }).subscribe(result => {
      // Opens new projectdialog
      const dialogRef = this.dialogBox.open(NewProjectDialog, {
        width: '450px',
        // @ts-ignore
        data: {Name: '', Templates: result._embedded.templates}
      });

      // After the dialog closers creates a new project
      // tslint:disable-next-line:no-shadowed-variable
      dialogRef.afterClosed().subscribe(result => {
        this.http.post(result.SelectedTemplate._links.project.href, {projectName: result.Name}, {
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
    }, error => {
      console.error(error);
    });
  }

  /**
   * openProject
   * Send the user to the kravEdit page using a specified project
   *
   * @param project
   * The project to send the user to edit
   */
  openProject(project) {
    this.userData.currentProject = project;
    this.userData.nav = 'kravEdit';
    localStorage.setItem('UserData', JSON.stringify(this.userData));
    this.router.navigate(['/kravEdit']);
  }

  /**
   * removeProject
   * Delets a project
   *
   * @param project
   * The project to delete
   */
  removeProject(project) {
    const dialogref = this.dialogBox.open(DeleteProjectDialog, {
      width: '300px'
    });

    dialogref.afterClosed().subscribe(result => {
      if (result) {
        this.http.delete(
          project._links.self.href,
          {
            headers: new HttpHeaders({
              Authorization: 'Bearer ' + this.userData.oauthClientSecret
            })
          }).subscribe(
          // tslint:disable-next-line:no-shadowed-variable
          result => {
            this.getActiveProjects();
          }, error => console.error(error));
      }
    });
  }

  /**
   * deleteToggle
   * Toggles the delete mode
   */
  deleteToggle() {
    this.deleteMode = !this.deleteMode;
  }

  /**
   * enterUserEdit
   *
   * Changes the current page tot eh user edit-page
   */
  enterUserEdit() {
    this.userData.nav = 'userEdit';
    localStorage.setItem('UserData', JSON.stringify(this.userData));
    this.router.navigate(['/userEdit']);
  }
}

export interface INewProject {
  Name: string;
  Templates: Template[];
  SelectedTemplate: Template;
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

@Component({
  // tslint:disable-next-line:component-selector
  selector: 'DeleteProject.Dialog',
  templateUrl: '../Modals/RemoveProject.Dialog.html'
})

// tslint:disable-next-line:component-class-suffix
export class DeleteProjectDialog {
  constructor(public dialogRef: MatDialogRef<DeleteProjectDialog>, @Inject(MAT_DIALOG_DATA) public data: boolean) {
    this.data = true;
  }

  onNoClick() {
    this.dialogRef.close();
  }
}
