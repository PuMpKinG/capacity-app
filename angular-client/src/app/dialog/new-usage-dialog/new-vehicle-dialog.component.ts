import {Component, Inject, OnInit} from '@angular/core';
import {Vehicle} from "../../app.types";
import {FormControl, Validators} from "@angular/forms";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {ConfirmDialogModel} from "../comfirm-dialog/confirm-dialog.component";

@Component({
    selector: 'app-new-usage-dialog',
    template: `
        <h2 mat-dialog-title>Neues Fahrzeugmodell anlegen</h2>
        <mat-dialog-content class="mat-typography">

            <form>
                <p>
                    <mat-form-field appearance="fill" class="p-r-s">
                        <mat-label>Marke</mat-label>
                        <input matInput name="company" [(ngModel)]="vehicle.company" placeholder="Marke"
                               [formControl]="companyFormControl" required>
                        <mat-error *ngIf="companyFormControl.invalid">{{getErrorMessage()}}</mat-error>
                    </mat-form-field>
                    
                    <mat-form-field appearance="fill">
                        <mat-label>Modell</mat-label>
                        <input matInput name="model" [(ngModel)]="vehicle.model" placeholder="Modell"
                               [formControl]="modelFormControl"
                               required>
                        <mat-error *ngIf="modelFormControl.invalid">{{getErrorMessage()}}</mat-error>
                    </mat-form-field>
                </p>
                <p>
                    <mat-form-field appearance="fill" class="p-r-s">
                        <mat-label>Breite</mat-label>
                        <input matInput name="width" [(ngModel)]="vehicle.width" placeholder="Breite"
                               [formControl]="widthFormControl" required>
                        <mat-error *ngIf="widthFormControl.invalid">{{getErrorMessage()}}</mat-error>
                    </mat-form-field>

                    <mat-form-field appearance="fill" class="p-r-s">
                        <mat-label>Länge</mat-label>
                        <input matInput name="length" [(ngModel)]="vehicle.length" placeholder="Länge"
                               [formControl]="lengthFormControl" required>
                        <mat-error *ngIf="lengthFormControl.invalid">{{getErrorMessage()}}</mat-error>
                    </mat-form-field>

                    <mat-form-field appearance="fill">
                        <mat-label>Höhe</mat-label>
                        <input matInput name="height" [(ngModel)]="vehicle.height" placeholder="Höhe"
                               [formControl]="heightFormControl" required>
                        <mat-error *ngIf="heightFormControl.invalid">{{getErrorMessage()}}</mat-error>
                    </mat-form-field>
                </p>
            </form>

        </mat-dialog-content>
        <mat-dialog-actions align="end">
            <button mat-button mat-dialog-close>Abbrechen</button>
            <button mat-button [mat-dialog-close]="vehicle" cdkFocusInitial>Speichern</button>
        </mat-dialog-actions>
    `,
    styles: []
})
export class NewVehicleDialog {

    vehicle: Vehicle = new Vehicle();
    companyFormControl = new FormControl('', [Validators.required]);
    modelFormControl = new FormControl('', [Validators.required]);
    lengthFormControl = new FormControl('', [Validators.required]);
    widthFormControl = new FormControl('', [Validators.required]);
    heightFormControl = new FormControl('', [Validators.required]);

    constructor(@Inject(MAT_DIALOG_DATA) public data: Vehicle) {
            this.vehicle = Object.assign(this.vehicle, data);
    }

    getErrorMessage() {
        if (this.companyFormControl?.hasError('required')) {
            return 'Hersteller angeben';
        }
        if (this.modelFormControl?.hasError('required')) {
            return 'Modell angeben';
        }
        if (this.lengthFormControl?.hasError('required')) {
            return 'Länge angeben';
        }
        if (this.widthFormControl?.hasError('required')) {
            return 'Breite angeben';
        }
        if (this.heightFormControl?.hasError('required')) {
            return 'Höhe angeben';
        }

        return '';
    }
}
