import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {VehicleOverviewComponent} from "./views/vehicle-overview/vehicle-overview.component";
import {VehicleUsageOverviewComponent} from "./views/vehicle-usage-overview/vehicle-usage-overview.component";
import {LoadCapacityComponent} from "./views/load-capacity/load-capacity.component";

const routes: Routes = [
  { path: '',   redirectTo: '/load-capacity', pathMatch: 'full' },
  { path: 'load-capacity', component: LoadCapacityComponent },
  { path: 'vehicle', component: VehicleOverviewComponent },
  { path: 'usage', component: VehicleUsageOverviewComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
