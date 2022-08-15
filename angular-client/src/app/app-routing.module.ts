import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LoadCapacityOverviewComponent} from "./views/load-capacity-overview/load-capacity-overview.component";
import {VehicleOverviewComponent} from "./views/vehicle-overview/vehicle-overview.component";
import {VehicleUsageOverviewComponent} from "./views/vehicle-usage-overview/vehicle-usage-overview.component";

const routes: Routes = [
  { path: '',   redirectTo: '/load-capacity', pathMatch: 'full' },
  { path: 'load-capacity', component: LoadCapacityOverviewComponent },
  { path: 'vehicle', component: VehicleOverviewComponent },
  { path: 'usage', component: VehicleUsageOverviewComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
