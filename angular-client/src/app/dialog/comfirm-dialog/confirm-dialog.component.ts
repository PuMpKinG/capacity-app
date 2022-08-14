import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-comfirm-dialog',
  template: `
    <h1 mat-dialog-title>
      {{title}}
    </h1>

    <div mat-dialog-content class="mat-typography">
      <p>{{message}}</p>
    </div>

    <div mat-dialog-actions>
      <button mat-button (click)="onDismiss()">Nein</button>
      <button mat-raised-button color="primary" (click)="onConfirm()">Ja</button>
    </div>
  `,
  styles: [
  ]
})
export class ConfirmDialogComponent {

  title: string;
  message: string;

  constructor(public dialogRef: MatDialogRef<ConfirmDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: ConfirmDialogModel) {
    // Update view with given values
    this.title = data.title;
    this.message = data.message;
  }

  onConfirm(): void {
    // Close the dialog, return true
    this.dialogRef.close(true);
  }

  onDismiss(): void {
    // Close the dialog, return false
    this.dialogRef.close(false);
  }
}

export class ConfirmDialogModel {
  constructor(public title: string, public message: string) {
  }
}
