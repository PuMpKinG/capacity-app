import {Util} from './util';

export type Prototype<Type> = new () => Type;
export type Converter<Type> = (object: any) => Type;

export class Vehicle {
    id: number | undefined;
    company: string | undefined;
    model: string | undefined;
    capacity: number | 0;
    length: number | 0;
    width: number | 0;
    height: number | 0;

    static convert: Converter<Vehicle> = (object: any) => {
        return Util.convertTo(Vehicle, object);
    };

    static convertArray: Converter<Vehicle[]> = (objects: any[]) => Util.convertArray(Vehicle.convert, objects);
}

export class VehicleUsage {
    id: number | undefined;
    lisencePlate: string | undefined;
    loadingTime: Date | undefined;
    unloadingTime: Date | undefined;
    usedCapacity: number | 0;
    vehicleState: string | undefined;
    vehicle: Vehicle;

    // virtual properties
    get freeCapacity() {
        return this.vehicle.capacity - this.usedCapacity;
    }

    get percentageCapacity() {
        return 100 / this.vehicle.capacity * this.usedCapacity;
    }

    get maxCapacity(){
        return this.vehicle.capacity;
    }

    static convert: Converter<VehicleUsage> = (object: any) => {
        let result: VehicleUsage = Util.convertTo(VehicleUsage, object);
        result.vehicle = Util.convertTo(Vehicle, object.vehicle);
        return result;
    };

    static convertArray: Converter<VehicleUsage[]> = (objects: any[]) => Util.convertArray(VehicleUsage.convert, objects);
}

export enum VehicleState {
    AVAILABLE = 'AVAILABLE', ON_STREET = 'ON_STREET', DESTROYED = 'DESTROYED'
}
