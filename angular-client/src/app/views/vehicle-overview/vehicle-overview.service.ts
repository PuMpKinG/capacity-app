import {Injectable} from "@angular/core";
import {LoggerService} from "../../service/logger.service";
import {map, Observable} from "rxjs";
import {Vehicle} from "../../app.types";
import {HttpService} from "../../service/http.service";
import {environment} from "../../../environments/environment";

@Injectable({
    providedIn: 'root'
})
export class VehicleOverviewService {

    private API_URL = environment.apiUrl
    constructor(private http: HttpService, private log: LoggerService) {
    }

    getVehicles(): Observable<Vehicle[]> {
        return this.http.get<Vehicle[]>(this.API_URL + '/vehicle').pipe(map(Vehicle.convertArray));
    }

    createVehicle(newVehicle: Vehicle):  Observable<Vehicle> {
        return this.http.post<Vehicle, Vehicle>(newVehicle, this.API_URL + '/vehicle').pipe(map(Vehicle.convert));
    }

    updateVehicle(update: Vehicle) {
        return this.http.put<Vehicle, undefined>(update, this.API_URL + '/vehicle/update/' + update.id);
    }

    deleteVehicle(toDelete: Vehicle){
        return this.http.delete<Vehicle>(this.API_URL + '/vehicle/delete/' + toDelete.id)
    }
}
