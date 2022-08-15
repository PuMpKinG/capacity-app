package de.chu.capacityapp.server.error;

public enum CapacityAppError {

    NOT_FOUND("Das angegebene Fahrzeug wurde nicht gefunden."),
    USAGE_EXISTS("Fahrzeug existiert bereits im Bestand."),
    USAGE_OVERLOAD("Fahrzeug überladen! Ladung / Max: $1 / $2"),
    USAGE_EMPTY("Fahrzeug ist bereits leer"),
    USAGE_TO_LESS("Fahrzeug hat nur %1 geladen, Entladung von: %2 nicht möglich"),
    
    VEHICLE_EXISTS("Ein Fahrzeug mit der Marke und Modell existiert bereits"),

    VEHICLE_USED("Fahrzeugmodell wird noch von einem Fahrzeug im Bestand referenziert. Kann nicht gelöscht werden");


    private String message;

    private CapacityAppError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
