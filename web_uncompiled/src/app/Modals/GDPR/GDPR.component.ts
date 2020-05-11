import {Component} from '@angular/core';
import {MatDialogRef} from '@angular/material/dialog';

@Component({
  selector: 'GDPR.Content',
  templateUrl: './GDPRContent.html',
})
export class GDPRContent {
  constructor(public dialogRef: MatDialogRef<GDPRContent>) {
  }

  /**
   * Is called once the dialog closes in this instance it will just close the dialog
   */
  OnNoClick() {
    this.dialogRef.close();
  }

}
