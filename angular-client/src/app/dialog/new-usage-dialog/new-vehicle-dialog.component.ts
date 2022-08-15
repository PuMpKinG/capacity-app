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
                               [formControl]="requiredFormControl" required>
                        <mat-error *ngIf="requiredFormControl.invalid">{{getErrorMessage()}}</mat-error>
                    </mat-form-field>
                    <mat-form-field appearance="fill">
                        <mat-label>Modell</mat-label>
                        <input matInput name="model" [(ngModel)]="vehicle.model" placeholder="Modell"
                               [formControl]="requiredFormControl"
                               required>
                        <mat-error *ngIf="requiredFormControl.invalid">{{getErrorMessage()}}</mat-error>
                    </mat-form-field>
                </p>
                <p>
                    <mat-form-field appearance="fill" class="p-r-s">
                        <mat-label>Breite</mat-label>
                        <input matInput name="width" [(ngModel)]="vehicle.width" placeholder="Breite"
                               [formControl]="requiredFormControl" required>
                        <mat-error *ngIf="requiredFormControl.invalid">{{getErrorMessage()}}</mat-error>
                    </mat-form-field>

                    <mat-form-field appearance="fill" class="p-r-s">
                        <mat-label>Länge</mat-label>
                        <input matInput name="length" [(ngModel)]="vehicle.length" placeholder="Länge"
                               [formControl]="requiredFormControl" required>
                        <mat-error *ngIf="requiredFormControl.invalid">{{getErrorMessage()}}</mat-error>
                    </mat-form-field>

                    <mat-form-field appearance="fill">
                        <mat-label>Höhe</mat-label>
                        <input matInput name="height" [(ngModel)]="vehicle.height" placeholder="Höhe"
                               [formControl]="requiredFormControl" required>
                        <mat-error *ngIf="requiredFormControl.invalid">{{getErrorMessage()}}</mat-error>
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
    requiredFormControl = new FormControl('', [Validators.required]);

    constructor(@Inject(MAT_DIALOG_DATA) public data: Vehicle) {
            this.vehicle = Object.assign(this.vehicle, data);
    }

    getErrorMessage() {
        if (this.requiredFormControl?.hasError('required')) {
            return 'You must enter a value';
        }

        return '';
    }
}
