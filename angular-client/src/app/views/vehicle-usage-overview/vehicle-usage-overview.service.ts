import {Injectable} from "@angular/core";
import {LoggerService} from "../../service/logger.service";
import {map, Observable} from "rxjs";
import {VehicleUsage} from "../../app.types";
import {HttpService} from "../../service/http.service";

@Injectable({
    providedIn: 'root'
})
export class VehicleUsageOverviewServices {

    private API_URL = 'http://localhost:8081/api'

    constructor(private http: HttpService, private log: LoggerService) {
    }

    getVehiclesUsages(): Observable<VehicleUsage[]> {
        return this.http.get<VehicleUsage[]>(this.API_URL + '/vehicle-usage').pipe(map(VehicleUsage.convertArray));
    }

    createVehicleUsage(newUsage: VehicleUsage): Observable<VehicleUsage> {
        return this.http.post<VehicleUsage, VehicleUsage>(newUsage, this.API_URL + '/vehicle-usage').pipe(map(VehicleUsage.convert));
    }

    deleteVehicleUsage(toDelete: VehicleUsage) {
        return this.http.delete<VehicleUsage>(this.API_URL + '/vehicle-usage/delete/' + toDelete.id)
    }
}
