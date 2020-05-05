import {Component, Inject, OnInit} from '@angular/core';
import {UserData} from '../models/UserData.model';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Router} from '@angular/router';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material/dialog';
import {TranslateService} from '@ngx-translate/core';
import {MatSnackBar} from '@angular/material';
import {DeleteUserDialog} from "../Modals/DeleteUser/DeleteUser.component";
import { PasswordChangeConfirmedDialog } from '../Modals/PasswordChangeConfirmed/PasswordChangeConfimred.component';

@Component({
  selector: 'app-root',
  templateUrl: './userEdit.component.html',
  styleUrls: [
    './userEdit.component.css',
    '../common.css'
  ]
})

// tslint:disable-next-line:class-name
export class userEditComponent implements  OnInit {
  userData: UserData;
  passwordForm: FormGroup;
  formBuilder: FormBuilder;
  newPassword: string;
  oldPassword: string;
  repeatPassword: string;

  private http: HttpClient;
  private router: Router;
  private snackBar: MatSnackBar;
  private dialogBox: MatDialog;

  constructor(http: HttpClient, router: Router, formBuilder: FormBuilder, public translate: TranslateService, snackBar: MatSnackBar, dialog: MatDialog) {
    this.http = http;
    this.router = router;
    this.formBuilder = formBuilder;
    this.snackBar = snackBar;
    this.dialogBox = dialog;
    translate.addLangs(['Bokmål', 'English', 'Nynorsk']);
    translate.setDefaultLang('Bokmål');
  }

  ngOnInit(): void {
    this.userData = JSON.parse(localStorage.getItem('UserData'));

    this.passwordForm = this.formBuilder.group({
      oldP: [ this.oldPassword, [
        Validators.required
      ]],
      newP: [ this.newPassword, [
        Validators.required
      ]],
      repP: [ this.repeatPassword, [
        Validators.required
      ]]
    });
  }

  logout() {
    localStorage.clear();
    this.http.get(this.userData._links['logout OAuth2'].href, {
      headers: new HttpHeaders({
        Authorization: 'Bearer ' + this.userData.oauthClientSecret
      })
    }).subscribe(result => {
    }, error => {
      console.error(error);
    });
    this.router.navigate(['/']);
    location.reload();
  }

  goToMainMenu() {
    this.userData.nav = 'Menu';
    localStorage.setItem('UserData', JSON.stringify(this.userData));
    this.router.navigate(['/Menu']);
  }

  /**
   * newPasswordSubmit
   *
   * Gets called when the user presses the change password button,
   * checks to se if all requirements are met then sends a request to the server
   */
  newPasswordSubmit() {
    if (this.newPassword !== this.repeatPassword) {
      // @ts-ignore
      this.snackBar.open(this.translate.get('ErrorsAndWarns.PasswordsDontMatch').value, this.translate.get('ErrorsAndWarns.Close').value);
    } else {
      // Checks if the old password is valid (Note that this only occurs clientside and is very easely bypassed)
      let body = new HttpParams();
      body = body.set('grant_type', 'password');
      body = body.append('username', this.userData.userName);
      body = body.append('password', this.oldPassword);

      this.http.post(this.userData._links['login OAuth2'].href, body, {
        // Constructs the headers
        headers: new HttpHeaders({
          Authorization: 'Basic ' + btoa(this.userData.oauthClientId + ':' + 'secret'),
          'Content-type': 'application/x-www-form-urlencoded; charset=utf-8'
        })
      }).subscribe(
        result => {
          this.submittNewPassword();
        }, error => {
          if (error.error.error_description === 'Bad credentials') {
            // @ts-ignore
            this.snackBar.open(this.translate.get('ErrorsAndWarns.WrongOldPass').value, this.translate.get('ErrorsAndWarns.Close').value);
          } else {
            console.error(error);
          }
        });
    }
  }

  /**
   * submittNewPassword
   *
   * After all checks have been fulfilled sends a request to the server to update the password of the current user
   */
  submittNewPassword() {
    this.http.patch(this.userData._links.konto.href, [{op: 'replace', path: '/password', value: this.newPassword}], {
      headers: new HttpHeaders({
        Authorization: 'Bearer ' + this.userData.oauthClientSecret
      })
    }).subscribe(result => {
      // Sends a request to invalidate the current token
      this.http.get(this.userData._links['logout OAuth2'].href, {
        headers: new HttpHeaders({
          Authorization: 'Bearer ' + this.userData.oauthClientSecret
        })
        // tslint:disable-next-line:no-shadowed-variable
      }).subscribe(result => {
        // After the old token has been invalidated, fetches a new one
        let body = new HttpParams();
        body = body.set('grant_type', 'password');
        body = body.append('username', this.userData.userName);
        body = body.append('password', this.newPassword);

        this.http.post(this.userData._links['login OAuth2'].href, body, {
          // Constructs the headers
          headers: new HttpHeaders({
            Authorization: 'Basic ' + btoa(this.userData.oauthClientId + ':' + 'secret'),
            'Content-type': 'application/x-www-form-urlencoded; charset=utf-8'
          })
        }).subscribe(
          // tslint:disable-next-line:no-shadowed-variable
          result => {
            // New token request fullfilled displays a modal and saves the new token
            // @ts-ignore
            this.userData.oauthClientSecret = result.access_token;
            localStorage.setItem('UserData', JSON.stringify(this.userData));

            this.dialogBox.open(PasswordChangeConfirmedDialog, {
              width: '450px'
            });
          }, error => {
            console.error(error);
          });
      }, error => {
        console.error(error);
      });
    }, error => {
      console.error(error);
    });
  }

  /**
   * deleteUser
   *
   * Is called when the user presses the delete user button in the GUI, will open a dialog where the user will confirm,
   * the request is also handled from within the dialog
   */
  deleteUser() {
    const ref = this.dialogBox.open(DeleteUserDialog, {
      width: '80%',
      maxWidth: '600px',
      data: this.http
    });
    ref.afterClosed().subscribe(result => {
      this.userData = JSON.parse(localStorage.getItem('UserData'));
    });
  }
}
