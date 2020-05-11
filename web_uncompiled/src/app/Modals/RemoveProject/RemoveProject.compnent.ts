import {Component, Inject} from "@angular/core";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";

@Component({
  // tslint:disable-next-line:component-selector
  selector: 'DeleteProject.Dialog',
  templateUrl: './RemoveProject.Dialog.html'
})
export class DeleteProjectDialog {
  constructor(public dialogRef: MatDialogRef<DeleteProjectDialog>, @Inject(MAT_DIALOG_DATA) public data: boolean) {
    this.data = true;
  }

  onNoClick() {
    this.dialogRef.close();
  }
}
