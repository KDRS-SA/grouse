import {Component, Inject, OnInit} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Router} from '@angular/router';
import {UserData} from '../models/UserData.model';
import {Project} from '../models/Project.model';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material/dialog';
import {TranslateService} from '@ngx-translate/core';
import {Template} from '../models/template';
import {Animations} from '../app.animations';
import { NewProjectDialog } from '../Modals/NewProject/NewProject.component';
import { DeleteProjectDialog } from '../Modals/RemoveProject/RemoveProject.compnent';

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
  public title = 'Grouse';

  http: HttpClient;
  userData: UserData;
  projects: Project[];
  dialogBox: MatDialog;
  deleteMode: boolean;
  shaking: number;

  private router: Router;

  constructor(http: HttpClient, router: Router, dialogBox: MatDialog, public translate: TranslateService) {
    this.http = http;
    this.router = router;
    this.userData = new UserData();
    this.dialogBox = dialogBox;
    translate.addLangs(['Bokmaal', 'English', 'Nynorsk']);
    translate.setDefaultLang('Bokmaal');
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
    this.translate.setDefaultLang(this.userData.defaultLang);
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
      console.error(error);
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
   * Send the user to the KravEdit page using a specified project
   *
   * @param project
   * The project to send the user to edit
   */
  openProject(project) {
    this.userData.currentProject = project;
    this.userData.nav = 'KravEdit';
    localStorage.setItem('UserData', JSON.stringify(this.userData));
    this.router.navigate(['/KravEdit']);
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

  /**
   * getMyProjects
   *
   * Returns an arry with projects only owned by you
   */
  getMyProjects(): Project[] {
    return this.projects.filter(project => project.ownedBy === this.userData.userName);
  }

  /**
   * changeLang
   * Changes the current language to the inputed via the lang parameter
   * @param lang The language to change into
   */
  changeLang(lang: string) {
    this.translate.use(lang);
    this.userData.defaultLang = lang;
    localStorage.setItem('UserData', JSON.stringify(this.userData));
  }

  /**
   * enterAdminPage
   * Opens the administration page, only available to admins
   */
  enterAdminPage() {
    if (this.userData._links["project-list-all"] === undefined){
      // The button should not be visible unless you have admin privileges
      console.warn('This user does not have admin privileges, you shouldt have been able to do this please contact an administrator!');
    } else {
      this.userData.nav = 'Admin';
      localStorage.setItem('UserData', JSON.stringify(this.userData));
      this.router.navigate(['/Admin']);
    }
  }
}

export interface INewProject {
  Name: string;
  Templates: Template[];
  SelectedTemplate: Template;
}
