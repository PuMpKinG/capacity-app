import {Component, ViewChild} from '@angular/core';
import {MatSidenav} from "@angular/material/sidenav";
import {Router} from "@angular/router";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  @ViewChild('sidenav') sidenav: MatSidenav | undefined;
  isExpanded = true;
  isShowing = false;

  constructor(private router: Router) {
  }

  mouseenter() {
    if (!this.isExpanded) {
      this.isShowing = true;
    }
  }

  mouseleave() {
    if (!this.isExpanded) {
      this.isShowing = false;
    }
  }

  openLoadCapacity = () => {
    this.router.navigate(["/load-capacity"]);
  }

  openVehicleUsage = () => {
    this.router.navigate(["/usage"]);
  }

  openVehicleModel = () => {
    this.router.navigate(["/vehicle"]);
  }

}
