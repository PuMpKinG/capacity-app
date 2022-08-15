import {Injectable} from "@angular/core";
import {LoggerService} from "../../service/logger.service";
import {map, Observable} from "rxjs";
import {Vehicle, VehicleUsage} from "../../app.types";
import {HttpService} from "../../service/http.service";
import {environment} from "../../../environments/environment";
import {VehicleUsageOverviewServices} from "../vehicle-usage-overview/vehicle-usage-overview.service";

@Injectable({
    providedIn: 'root'
})
export class LoadCapacityService extends  VehicleUsageOverviewServices{

    loadGoodsOnVehicle(vehicleUsage: VehicleUsage, load: number): Observable<VehicleUsage> {
        return this.http.put<undefined, VehicleUsage>(undefined,
            `${this.API_URL}/vehicle-usage/${vehicleUsage.id}/load/${load}`).pipe(map(VehicleUsage.convert));
    }

    unloadGoodsOnVehicle(vehicleUsage: VehicleUsage, unload: number): Observable<VehicleUsage> {
        return this.http.put<undefined, VehicleUsage>(undefined,
            `${this.API_URL}/vehicle-usage/${vehicleUsage.id}/unload/${unload}`).pipe(map(VehicleUsage.convert));
    }

    changeState(vehicleUsage: VehicleUsage, state: string): Observable<VehicleUsage> {
        return this.http.put<undefined, VehicleUsage>(undefined,
            `${this.API_URL}/vehicle-usage/${vehicleUsage.id}/state/${state}`).pipe(map(VehicleUsage.convert));
    }
}
