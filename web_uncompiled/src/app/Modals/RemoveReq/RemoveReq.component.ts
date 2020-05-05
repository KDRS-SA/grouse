import {Component, Inject} from "@angular/core";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";

@Component({
  // tslint:disable-next-line:component-selector
  selector: 'DeleteRequirment.Dialog',
  templateUrl: './RemoveReq.Dialog.html'
})
// tslint:disable-next-line:component-class-suffix
export class DeleteRequirmentDialog {
  constructor(public dialogRef: MatDialogRef<DeleteRequirmentDialog>, @Inject(MAT_DIALOG_DATA) public data: boolean) {
    this.data = true;
  }

  onNoClick() {
    this.dialogRef.close();
  }
}
