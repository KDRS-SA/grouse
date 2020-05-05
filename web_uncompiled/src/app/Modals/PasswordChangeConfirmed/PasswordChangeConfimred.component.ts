import {Component} from "@angular/core";
import {MatDialogRef} from "@angular/material/dialog";

@Component({
  // tslint:disable-next-line:component-selector
  selector: 'PasswordChangeConfirmed.Dialog',
  templateUrl: './PasswordChangeConfirmed.Dialog.html',
  styleUrls: [
    '../../UserEdit/userEdit.component.css',
    '../../common.css'
  ]
})
// tslint:disable-next-line:component-class-suffix
export class PasswordChangeConfirmedDialog {
  constructor(public dialogRef: MatDialogRef<PasswordChangeConfirmedDialog>) {
  }

  onNoClick() {
    this.dialogRef.close();
  }
}
