import {Injectable} from "@angular/core";
import {LoggerService} from "../../service/logger.service";
import {map, Observable} from "rxjs";
import {VehicleUsage} from "../../app.types";
import {HttpService} from "../../service/http.service";
import {environment} from "../../../environments/environment";

@Injectable({
    providedIn: 'root'
})
export class VehicleUsageOverviewServices {

    protected API_URL = environment.apiUrl

    constructor(protected http: HttpService, private log: LoggerService) {
    }

    getVehiclesUsages(filterOvAvailable?: boolean): Observable<VehicleUsage[]> {
        let url = this.API_URL + '/vehicle-usage';
        if (filterOvAvailable) {
            url += '?filterOnAvailable=true'
        }

        return this.http.get<VehicleUsage[]>(url).pipe(map(VehicleUsage.convertArray));
    }

    createVehicleUsage(newUsage: VehicleUsage): Observable<VehicleUsage> {
        return this.http.post<VehicleUsage, VehicleUsage>(newUsage, this.API_URL + '/vehicle-usage').pipe(map(VehicleUsage.convert));
    }

    deleteVehicleUsage(toDelete: VehicleUsage) {
        return this.http.delete<VehicleUsage>(this.API_URL + '/vehicle-usage/delete/' + toDelete.id)
    }
}
