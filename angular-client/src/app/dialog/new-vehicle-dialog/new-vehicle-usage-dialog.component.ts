import {Component, OnInit} from '@angular/core';
import {Vehicle, VehicleUsage} from "../../app.types";
import {FormControl, Validators} from "@angular/forms";
import {VehicleOverviewService} from "../../views/vehicle-overview/vehicle-overview.service";

@Component({
    selector: 'app-new-vehicle-dialog',
    template: `
        <h2 mat-dialog-title>Neues Fahrzeug im Bestand hinzufügen</h2>
        <mat-dialog-content class="mat-typography">

            <form>
                <p>
                    <mat-form-field appearance="fill" class="p-r-s">
                        <mat-label>Kennzeichen</mat-label>
                        <input matInput name="lisencePlate" [(ngModel)]="vehicleUsage.lisencePlate" placeholder="Kennzeichen"
                               [formControl]="licenseFormControl" required>
                        <mat-error *ngIf="licenseFormControl.invalid">{{getErrorMessage()}}</mat-error>
                    </mat-form-field>
                </p>
                <p>
                    <mat-form-field appearance="fill" class="p-r-s">
                        <mat-label>Modell</mat-label>
                        <mat-select [formControl]="vehicledFormControl" required 
                                    [(ngModel)]="selectedModel" (selectionChange)="selectionChanged()">
                            <mat-option>--</mat-option>
                            <mat-option *ngFor="let vehicle of availableVehicles" [value]="vehicle">
                                {{vehicle.company + ' ' + vehicle.model}}
                            </mat-option>
                        </mat-select>
                        <mat-error *ngIf="vehicledFormControl.invalid">{{getErrorMessage()}}</mat-error>
                    </mat-form-field>
                </p>
            </form>

        </mat-dialog-content>
        <mat-dialog-actions align="end">
            <button mat-button mat-dialog-close>Abbrechen</button>
            <button mat-button [mat-dialog-close]="vehicleUsage" cdkFocusInitial>Speichern</button>
        </mat-dialog-actions>
    `,
    styles: []
})
export class NewVehicleUsageDialogComponent implements OnInit{


    availableVehicles: Vehicle[] = [];
    selectedModel: Vehicle;
    vehicleUsage: VehicleUsage;
    licenseFormControl = new FormControl('', [Validators.required]);
    vehicledFormControl = new FormControl('', [Validators.required]);

    constructor(private vehicleService: VehicleOverviewService) {
        this.vehicleUsage = new VehicleUsage();
    }

    ngOnInit(): void {
        this.vehicleService.getVehicles().subscribe((response) => {
            this.availableVehicles = response;
        });
    }

    selectionChanged() {
        this.vehicleUsage.vehicle = this.selectedModel;
    }

    getErrorMessage() {
        if (this.licenseFormControl?.hasError('required')) {
            return 'Kennzeichen angeben';
        }

        if(this.vehicledFormControl?.hasError('required')) {
            return 'Fahrzeugmodell auswählen';
        }

        return '';
    }
}
