import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {ConfirmDialogModel} from "../comfirm-dialog/confirm-dialog.component";

@Component({
  selector: 'app-prompt-dialog',
  template: `
    <h1 mat-dialog-title>
      {{title}}
    </h1>

    <div mat-dialog-content class="mat-typography">
      <p>{{message}}</p>
    </div>

    <mat-form-field>
      <input matInput name="value" [(ngModel)]="value">
    </mat-form-field>

    <div mat-dialog-actions>
      <button mat-button (click)="onDismiss()">Nein</button>
      <button mat-raised-button color="primary" (click)="onConfirm()">Ja</button>
    </div>
  `,
  styles: [
  ]
})
export class PromptDialogComponent  {

  title: string;
  message: string;
  value: string

  constructor(public dialogRef: MatDialogRef<PromptDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: ConfirmDialogModel) {
    // Update view with given values
    this.title = data.title;
    this.message = data.message;
  }

  onConfirm(): void {
    // Close the dialog, return true
    this.dialogRef.close(this.value);
  }

  onDismiss(): void {
    // Close the dialog, return false
    this.dialogRef.close(null);
  }
}
