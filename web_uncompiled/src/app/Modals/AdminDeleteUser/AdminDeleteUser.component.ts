import {Component, Inject} from "@angular/core";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";

@Component({
  // tslint:disable-next-line:component-selector
  selector: 'AdminDeleteUser.Dialog',
  templateUrl: './AdminDeleteUser.Dialog.html',
  styleUrls: [
    '../../Admin/Admin.component.css',
    '../../common.css'
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
