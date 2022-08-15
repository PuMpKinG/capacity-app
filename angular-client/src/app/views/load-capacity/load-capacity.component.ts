import {AfterViewInit, ChangeDetectorRef, Component, OnInit, ViewChild} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {VehicleState, VehicleUsage} from "../../app.types";
import {MatSort} from "@angular/material/sort";
import {LoadCapacityService} from "./load-capacity.service";
import {MatDialog} from "@angular/material/dialog";
import {ConfirmDialogComponent, ConfirmDialogModel} from "../../dialog/comfirm-dialog/confirm-dialog.component";
import {PromptDialogComponent} from "../../dialog/prompt-dialog/prompt-dialog.component";
import {SelectionModel} from "@angular/cdk/collections";

@Component({
    selector: 'app-load-capacity-overview',
    templateUrl: './load-capacity.component.html',
    styleUrls: ['./load-capacity.component.scss']
})
export class LoadCapacityComponent implements AfterViewInit {

    filterOnAvailable: boolean = false;
    displayedColumns = ['lisencePlate', 'usedCapacity', 'freeCapacity', 'maxCapacity', 'capacity', 'loadingTime', 'unloadingTime', 'vehicleState', 'vehicle', 'measure'];
    dataSource: MatTableDataSource<VehicleUsage>;
    selection = new SelectionModel<VehicleUsage>(false, undefined);
    cachedUsages: VehicleUsage[] = [];

    get selected(): VehicleUsage {
        return this.selection.selected[0];
    }

    @ViewChild(MatSort, {static: false}) sort: MatSort;

    constructor(private loadCapacityService: LoadCapacityService, public dialog: MatDialog, private cdr: ChangeDetectorRef) {
    }

    /**
     * Set the paginator and sort after the view init since this component will
     * be able to query its view for the initialized paginator and sort.
     */
    ngAfterViewInit() {
        this.loadCapacityService.getVehiclesUsages().subscribe((usages) => {
            this.cachedUsages = usages;
            this.dataSource = new MatTableDataSource(this.cachedUsages);
            this.dataSource.sort = this.sort;
        })

    }

    applyFilter(filterValue: string) {
        filterValue = filterValue.trim(); // Remove whitespace
        filterValue = filterValue.toLowerCase(); // Datasource defaults to lowercase matches
        this.dataSource.filter = filterValue;
    }

    loadVehicle() {
        const dialogRef = this.dialog.open(PromptDialogComponent, {
            maxWidth: "400px",
            data: new ConfirmDialogModel("Fahrzeug beladen", "Wie viel Lademeter soll auf das Fahrzeug geladen werden?")
        });

        dialogRef.afterClosed().subscribe((result: string) => {
            if (result) {
                let toLoad = Number.parseFloat(result)
                let newLoad = toLoad + this.selected.usedCapacity;
                let maxCapacity = this.selected.vehicle?.capacity || 0;
                if (newLoad > maxCapacity) {
                    alert("Fahrzeug hat nicht ausreichend Platz: (" + newLoad + "/" + maxCapacity + ")");
                    return;
                }

                this.loadCapacityService.loadGoodsOnVehicle(this.selected, toLoad).subscribe(this.updateInTable);
            }
        });
    }

    unloadVehicle() {
        const dialogRef = this.dialog.open(PromptDialogComponent, {
            maxWidth: "400px",
            data: new ConfirmDialogModel("Fahrzeug entladen", "Wie viel Lademeter soll von dem Fahrzeug entladen werden?")
        });

        dialogRef.afterClosed().subscribe((result: string) => {
            if (result) {
                let toUnload = Number.parseFloat(result)
                let newLoad = this.selected.usedCapacity - toUnload;
                // if (newLoad < 0) {
                //     alert("Fahrzeug hat nur weniger Lademeter zum Entladen zur Verfügung: (" + toUnload + ")");
                //     return;
                // }

                this.loadCapacityService.unloadGoodsOnVehicle(this.selected, toUnload).subscribe(this.updateInTable);
            }
        });
    }

    vehicleDefect() {
        this.setState("Defekt", "Ist das Fahrzeug defekt?", VehicleState.DESTROYED);
    }

    vehicleOnRoad() {
        this.setState("Auf Reisen", "Ist das Fahrzeug unterwegs?", VehicleState.ON_STREET);
    }

    vehicleOnStorage() {
        this.setState("Im Lager", "Ist das Fahrzeug im Lager?", VehicleState.AVAILABLE);
    }

    setState(title: string, msg: string, type: VehicleState) {
        const dialogRef = this.dialog.open(ConfirmDialogComponent, {
            maxWidth: "400px",
            data: new ConfirmDialogModel(title, msg)
        });

        dialogRef.afterClosed().subscribe((result: boolean) => {
            if (result) {
                this.loadCapacityService.changeState(this.selected, type).subscribe(this.updateInTable);
            }
        });

    }

    filterAvailableChanged() {
        this.loadCapacityService.getVehiclesUsages(this.filterOnAvailable).subscribe((usages) => {
            this.cachedUsages = usages;
            this.dataSource.data = this.cachedUsages;
        })
    }

    updateInTable = (update: VehicleUsage) => {
        const idx = this.cachedUsages.findIndex(itm => itm.id === update.id);
        if (idx) {
            this.cachedUsages[idx] = update;
        }
        this.dataSource.data = [...this.cachedUsages];

        if (this.filterOnAvailable) {
            this.filterAvailableChanged();
        }
    }


}
