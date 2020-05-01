import {Component, Inject} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {UserData} from "../../models/UserData.model";
import {User} from "../../models/User";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";

@Component({
  // tslint:disable-next-line:component-selector
  selector: 'ShareMenu.Dialog',
  templateUrl: './ShareMenu.Dialog.html',
  styleUrls: [
    '../../KravEdit/kravEdit.component.css',
    '../../common.css'
  ]
})
/**
 * ShareMenu
 * The dialog where a user can select other users to partake in the work of a project
 */
export class ShareMenu {
  private http: HttpClient;
  userData: UserData;
  shares: User[];
  formgroup: FormGroup;
  newShare: string;

  /**
   * ShareMenuConstructor
   * @param dialogRef This dialogrefrence
   * @param data Data wich the dialog can use, in this case the userData
   * @param http The HTTP Client wich the dialog will use to comunicate with the server
   * @param formBuilder The form Builder wich will be used to validate that the user enters valid emails
   */
  constructor(public dialogRef: MatDialogRef<ShareMenu>, @Inject(MAT_DIALOG_DATA) public data: UserData, http: HttpClient, private formBuilder: FormBuilder) {
    this.userData = data;
    this.http = http;
    this.shares = [];
    this.getActiveShares();

    this.formgroup = formBuilder.group({
      email: [this.newShare, [
        Validators.required,
        Validators.email
      ]],
    });
  }

  getActiveShares() {
    this.http.get(this.userData.currentProject._links.access.href, {
      headers: new HttpHeaders({
        Authorization: 'Bearer ' + this.userData.oauthClientSecret
      })
    }).subscribe(result => {
      // @ts-ignore
      this.shares = result._embedded.users;
      console.log(result);
      // tslint:disable-next-line:no-shadowed-variable
    }, error => {
      console.error(error);
    });
  }

  addShare() {
    this.http.post(this.userData.currentProject._links.share.href.replace('user_email_address', this.newShare), {}, {
      headers: new HttpHeaders({
        Authorization: 'Bearer ' + this.userData.oauthClientSecret
      })
    }).subscribe(result => {
      this.getActiveShares();
    }, error => {
      console.error(error);
    });
  }

  revokeShare(user: string) {
    this.http.delete(this.userData.currentProject._links.share.href.replace('user_email_address', user), {
      headers: new HttpHeaders({
        Authorization: 'Bearer ' + this.userData.oauthClientSecret
      })
    }).subscribe(result => {
      this.getActiveShares();
    }, error => {
      console.error(error);
    });
  }

  onNoClick() {
    this.dialogRef.close();
  }
}
