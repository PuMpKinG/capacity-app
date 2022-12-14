import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {Vehicle, VehicleUsage} from "../../app.types";
import {MatDialog} from "@angular/material/dialog";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {NewVehicleUsageDialogComponent} from "../../dialog/new-vehicle-dialog/new-vehicle-usage-dialog.component";
import {ConfirmDialogComponent, ConfirmDialogModel} from "../../dialog/comfirm-dialog/confirm-dialog.component";
import {VehicleUsageOverviewServices} from "./vehicle-usage-overview.service";
import {Subscription} from "rxjs";


@Component({
    selector: 'app-usage-overview',
    templateUrl: './vehicle-usage-overview.component.html',
    styleUrls: ['./vehicle-usage-overview.component.scss']
})
export class VehicleUsageOverviewComponent implements AfterViewInit {

    displayedColumns = ['lisencePlate', 'capacity', 'vehicleState', 'vehicle', 'delete'];
    dataSource: MatTableDataSource<VehicleUsage>;

    @ViewChild(MatSort, {static: false}) sort: MatSort;
    @ViewChild(MatPaginator, {static: false}) paginator: MatPaginator;

    constructor(private usageService: VehicleUsageOverviewServices, public dialog: MatDialog) {
    }

    /**
     * Set the paginator and sort after the view init since this component will
     * be able to query its view for the initialized paginator and sort.
     */
    ngAfterViewInit() {
        this.usageService.getVehiclesUsages().subscribe((usage) => {
            this.dataSource = new MatTableDataSource(usage);
            this.dataSource.paginator = this.paginator;
            this.dataSource.sort = this.sort;
        })
    }

    openDialog() {
        const dialogRef = this.dialog.open(NewVehicleUsageDialogComponent);

        dialogRef.afterClosed().subscribe((result: VehicleUsage) => {
            if (result) {
                console.log(result);
                this.usageService.createVehicleUsage(result).subscribe((response: VehicleUsage) => {
                    this.dataSource.data = [...this.dataSource.data, response];
                })
            }
        });
    }

    deleteUsage(vehicleUsage: VehicleUsage) {
        const dialogRef = this.dialog.open(ConfirmDialogComponent, {
            maxWidth: "400px",
            data: new ConfirmDialogModel("L??schen?", "Soll das Fahrzeug wirklich aus dem Bestand gel??scht werden?")
        });

        dialogRef.afterClosed().subscribe((result: boolean) => {
            if (result) {
                this.usageService.deleteVehicleUsage(vehicleUsage).subscribe(() => {
                    this.refreshVehicles();
                })
            }
        });
    }

    refreshVehicles() {
        this.usageService.getVehiclesUsages().subscribe((vehicleUsages) => {
            this.dataSource.data = vehicleUsages;
        })
    }

    applyFilter(filterValue: string) {
        filterValue = filterValue.trim(); // Remove whitespace
        filterValue = filterValue.toLowerCase(); // Datasource defaults to lowercase matches
        this.dataSource.filter = filterValue;
    }

}
