import {Component, Inject} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {FormBuilder, FormGroup} from "@angular/forms";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {IConcurrencyDetails} from "../../KravEdit/kravEdit.component";

@Component({
  // tslint:disable-next-line:component-selector
  selector: 'ConcurrencyResolver.Dialog',
  templateUrl: './ConcurrencyResolver.Dialog.html',
  styleUrls: [
    '../../KravEdit/kravEdit.component.css',
    '../../common.css'
  ]
})

export class ConcurrencyResolver {
  private http: HttpClient;
  private ConcurrencyDetails: IConcurrencyDetails;
  formControl: FormGroup;
  clientVersion: string;
  serverVersion: string;
  newVersion: string;
  type: string;
  radioOption: string;

  private currentEtag: string;

  constructor(public dialogRef: MatDialogRef<ConcurrencyResolver>, @Inject(MAT_DIALOG_DATA) public data: IConcurrencyDetails, http: HttpClient, private formBuilder: FormBuilder) {
    this.ConcurrencyDetails = data;
    this.http = http;
    this.dialogRef.disableClose = true;
    this.type = data.patch[0].path.substr(1);
    this.formControl = formBuilder.group({});
    this.clientVersion = data.patch[0].value;
    this.serverVersion = "";
    this.currentEtag = '"0"';
    this.radioOption = "";
    this.fetchFromServer();
  }

  fetchFromServer(){
    this.http.get(this.data.url, {
      headers: new HttpHeaders({
        Authorization: 'Bearer ' + this.data.token
      }),
      observe: "response"
    }).subscribe(result => {
      if( this.type === "requirementText") {
        // @ts-ignore
        this.serverVersion = result.body.requirementText;
      } else if ( this.type === "priority") {
        // @ts-ignore
        this.serverVersion = result.body.priority;
      } else {
        console.error("Wrong type!")
        console.error(result.body);
        this.dialogRef.close(null);
      }
      this.currentEtag = result.headers.get('etag');
    }, error => {
      console.error(error);
    })
  }

  updateRequirementPriority(field: string, update: string) {
    if (field === "client") {
      this.clientVersion = update;
    } else if (field === "server") {
      this.serverVersion = update;
    }
  }

  onNoClick() {
    //The value that was selected
    let value = "";
    if(this.radioOption === "client") {
      value = this.clientVersion;
    } else if (this.radioOption === "server") {
      value = this.serverVersion;
    } else if (this.radioOption === "new") {
      value = this.newVersion;
    }

    // Creating the patch object
    let patch = [{}];
    if (this.type === "requirementText") {
      patch = [{op: "replace", path: "/requirementText", value: value}];
    } else if ( this.type === "priority") {
      patch = [{op: "replace", path: "/priority", value: value}]
    }

    this.http.patch(this.data.url, patch, {
      headers: new HttpHeaders({
        Authorization: 'Bearer ' + this.data.token,
        ETAG: this.currentEtag
      }),
      observe: "response"
    }).subscribe(result => {
      let returned: any = result.body;
      returned.etag = result.headers.get("etag");
      this.dialogRef.close(returned);
    }, error => {
      console.error(error);
      this.dialogRef.close(null);
    })
  }
}
