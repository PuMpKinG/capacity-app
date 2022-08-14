import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoadCapacityOverviewComponent } from './views/load-capacity-overview/load-capacity-overview.component';
import { VehicleOverviewComponent } from './views/vehicle-overview/vehicle-overview.component';
import { UsageOverviewComponent } from './views/usage-overview/usage-overview.component';

@NgModule({
  declarations: [
    AppComponent,
    LoadCapacityOverviewComponent,
    VehicleOverviewComponent,
    UsageOverviewComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
