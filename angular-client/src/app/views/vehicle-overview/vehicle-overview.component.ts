import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {Vehicle} from "../../app.types";
import {VehicleOverviewService} from "./vehicle-overview.service";
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {DataSource} from "@angular/cdk/collections";
import {Observable, ReplaySubject} from "rxjs";
import {MatDialog} from "@angular/material/dialog";
import {NewVehicleDialog} from "../../dialog/new-usage-dialog/new-vehicle-dialog.component";
import {ConfirmDialogComponent, ConfirmDialogModel} from "../../dialog/comfirm-dialog/confirm-dialog.component";
import {NewVehicleUsageDialogComponent} from "../../dialog/new-vehicle-dialog/new-vehicle-usage-dialog.component";

@Component({
    selector: 'app-vehicle-overview',
    templateUrl: './vehicle-overview.component.html',
    styleUrls: ['./vehicle-overview.component.scss']
})
export class VehicleOverviewComponent implements OnInit, AfterViewInit {

    displayedColumns = ['company', 'model', 'width', 'length', 'height', 'modify'];
    dataSource: MatTableDataSource<Vehicle>;

    @ViewChild(MatPaginator, {static: false}) paginator: MatPaginator;
    @ViewChild(MatSort, {static: false}) sort: MatSort;

    constructor(private vehicleService: VehicleOverviewService, public dialog: MatDialog) {
    }

    ngOnInit(): void {
        this.vehicleService.getVehicles().subscribe((vehicles) => {
            this.dataSource = new MatTableDataSource(vehicles);
        })
    }

    /**
     * Set the paginator and sort after the view init since this component will
     * be able to query its view for the initialized paginator and sort.
     */
    ngAfterViewInit() {
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
    }

    openDialog(vehicle?: Vehicle) {
        const dialogRef = this.dialog.open(NewVehicleDialog, {data: vehicle ? vehicle : new Vehicle()});

        dialogRef.afterClosed().subscribe((result: Vehicle) => {
            console.log(result);
            this.vehicleService.createVehicle(result).subscribe((response: Vehicle) => {
                this.dataSource.data = [...this.dataSource.data, response];
            })
        });
    }

    deleteVehicle(vehicle: Vehicle) {
        const dialogRef = this.dialog.open(ConfirmDialogComponent, {
            maxWidth: "400px",
            data: new ConfirmDialogModel("Löschen?", "Soll das Fahrzeugmodel wirklich gelöscht werden?")
        });

        dialogRef.afterClosed().subscribe((result: boolean) => {
            if (result) {
                this.vehicleService.deleteVehicle(vehicle).subscribe(() => {
                    this.refreshVehicles();
                })
            }
        });
    }

    editVehicle(vehicle: Vehicle) {
        this.openDialog(vehicle);
    }

    refreshVehicles() {
        this.vehicleService.getVehicles().subscribe((vehicles) => {
            this.dataSource.data = vehicles;
        })
    }

    applyFilter(filterValue: string) {
        filterValue = filterValue.trim(); // Remove whitespace
        filterValue = filterValue.toLowerCase(); // Datasource defaults to lowercase matches
        this.dataSource.filter = filterValue;
    }

}

