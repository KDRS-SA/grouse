import {Component, Inject} from "@angular/core";
import {UserData} from "../../models/UserData.model";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";

@Component({
  // tslint:disable-next-line:component-selector
  selector: 'DeleteUser.Dialog',
  templateUrl: './DeleteUser.Dialog.html',
  styleUrls: [
    '../../UserEdit/userEdit.component.css',
    '../../common.css'
  ]
})
// tslint:disable-next-line:component-class-suffix
export class DeleteUserDialog {
  userData: UserData;
  checked: boolean;
  loading: boolean;
  deleted: boolean;
  // too many states of authorization wanted to illustrate them all graphically {0 = un-authorized, 1 = checking with server, -1 = wrong password, 2 = authorized}
  authorized: number;
  error;
  formGroup: FormGroup;
  pass: string;

  private router: Router;

  constructor(public dialogRef: MatDialogRef<DeleteUserDialog>, @Inject(MAT_DIALOG_DATA) public data: HttpClient, formBuilder: FormBuilder, router: Router) {
    this.checked = false;
    this.loading = false;
    this.deleted = false;
    this.error = null;
    this.authorized = 0;
    this.pass = '';
    this.router = router;
    this.userData = JSON.parse(localStorage.getItem('UserData'));
    this.formGroup = formBuilder.group({
      Pass: [ this.pass, [
        Validators.required
      ]]
    });
  }

  confirm() {
    this.loading = true;
    this.dialogRef.disableClose = true;
    this.data.delete(this.userData._links.konto.href, {
      headers: new HttpHeaders({
        Authorization: 'Bearer ' + this.userData.oauthClientSecret
      })
    }).subscribe(result => {
      if (result === null) {
        this.deleted = true;
        this.dialogRef.disableClose = false;
        this.loading = false;
      }
    }, error => {
      this.loading = false;
      this.error = error;
      console.error(error);
    });
  }

  checkPass() {
    this.authorized = 1;
    this.dialogRef.disableClose = true;
    // Checks if the old password is valid (Note that this only occurs clientside and is very easely bypassed)
    let body = new HttpParams();
    body = body.set('grant_type', 'password');
    body = body.append('username', this.userData.userName);
    body = body.append('password', this.pass);

    this.data.post(this.userData._links['login OAuth2'].href, body, {
      // Constructs the headers
      headers: new HttpHeaders({
        Authorization: 'Basic ' + btoa(this.userData.oauthClientId + ':' + 'secret'),
        'Content-type': 'application/x-www-form-urlencoded; charset=utf-8'
      })
    }).subscribe(
      result => {
        // @ts-ignore
        this.userData.oauthClientSecret = result.access_token;
        this.authorized = 2;
      }, error => {
        if (error.error.error_description === 'Bad credentials') {
          // @ts-ignore
          this.authorized = -1;
        } else {
          console.error(error);
        }
      });
  }

  logout() {
    localStorage.clear();
    this.data.get(this.userData._links['logout OAuth2'].href, {
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

  onNoClick() {
    if (this.deleted) {
      localStorage.clear();
      window.location.reload();
    } else if (this.authorized) {
      // If the token has been altered by checking for the correct password it must be updated
      localStorage.setItem('UserData', JSON.stringify(this.userData));
      this.dialogRef.close();
    } else {
      this.dialogRef.close();
    }
  }
}
