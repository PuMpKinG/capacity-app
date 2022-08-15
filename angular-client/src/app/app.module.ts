import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {VehicleOverviewComponent} from './views/vehicle-overview/vehicle-overview.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatSidenavModule} from "@angular/material/sidenav";
import {MatListModule} from "@angular/material/list";
import {MatIconModule} from "@angular/material/icon";
import {MatToolbarModule} from "@angular/material/toolbar";
import {HttpClientModule} from "@angular/common/http";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatTableModule} from "@angular/material/table";
import {MatPaginatorModule} from "@angular/material/paginator";
import {NewVehicleUsageDialogComponent} from './dialog/new-vehicle-dialog/new-vehicle-usage-dialog.component';
import {NewVehicleDialog} from './dialog/new-usage-dialog/new-vehicle-dialog.component';
import {MatDialogModule} from "@angular/material/dialog";
import {MatButtonModule} from "@angular/material/button";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {ConfirmDialogComponent} from './dialog/comfirm-dialog/confirm-dialog.component';
import {VehicleUsageOverviewComponent} from "./views/vehicle-usage-overview/vehicle-usage-overview.component";
import {MatSelectModule} from "@angular/material/select";
import {LoadCapacityComponent} from "./views/load-capacity/load-capacity.component";
import {MatProgressBarModule} from "@angular/material/progress-bar";
import { PromptDialogComponent } from './dialog/prompt-dialog/prompt-dialog.component';
import {MatCheckboxModule} from "@angular/material/checkbox";
import {MatSortModule} from "@angular/material/sort";

@NgModule({
    declarations: [
        AppComponent,
        LoadCapacityComponent,
        VehicleOverviewComponent,
        VehicleUsageOverviewComponent,
        NewVehicleUsageDialogComponent,
        NewVehicleDialog,
        ConfirmDialogComponent,
        PromptDialogComponent,
    ],
    imports: [
        HttpClientModule,
        BrowserModule,
        AppRoutingModule,
        BrowserAnimationsModule,
        MatSidenavModule,
        MatListModule,
        MatIconModule,
        MatToolbarModule,
        MatFormFieldModule,
        MatInputModule,
        MatTableModule,
        MatPaginatorModule,
        MatDialogModule,
        MatButtonModule,
        ReactiveFormsModule,
        FormsModule,
        MatSelectModule,
        MatProgressBarModule,
        MatCheckboxModule,
        MatSortModule
    ],
    providers: [],
    bootstrap: [AppComponent],
    exports: [
        NewVehicleUsageDialogComponent,
        NewVehicleDialog,
        ConfirmDialogComponent,
        PromptDialogComponent
    ]
})
export class AppModule {
}
