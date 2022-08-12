# Aufgabe

Wir möchten eine neue Applikation entwickeln, die Informationen zur Laderaumauslastung mehrerer Fahrzeuge aufnimmt und wiedergibt, damit freie Kapazitäten ersichtlich werden und ggfs. umgeplant werden können.

Bedingungen: JDK 8+, Spring Boot, keine lizenzpflichtigen Bibliotheken, nur Open Source Frameworks oder eigenen, einsehbaren Custom Code verwenden.

Funktionen und Constraints:

    Web App
    CRUD, um die Domaine zu verwalten
    Liste mit Fahrzeugen und Auslastung sowie Freie Kapazitäten
    Single-User-Nutzung (ohne Anmeldungsbedarf)
    Filter, welche Fahrzeuge freie Kapazitäten haben

Domaine:

    ID
    Fahrzeug(-kennung)
    Beladezeit
    Entladezeit
    Laderaum (Höhe, Breite, Tiefe)
    Belegung (z.B. in Europlatten oder Lademeter)
    ggfs. Blocking (weil Fahrzeug unterwegs oder defekt ist)

Unser Ziel mit dieser Aufgabe ist es Deine Arbeitsweise kennenzulernen und Deine Entscheidungen für bestimmte Vorgehensweisen zu verstehen.

## Umsetzung
Wie folgt wird das ganze umgesetzt:

### Model 
Vehicle
* id
* license
* loadingTime
* unloadingTime
* usedCapacity
* vehicleStatus (enum)
* vehicleTypeRef
> Was ist mit dem Belade- und Entladeort?

VehicleType
* id
* company
* model
* capacity (transient)
* loadCompartmentRef

LoadCompartment
* height
* width
* length


### Funktionen
* Fahrzeugverwaltung
  * Fahrzeug anlegen
  * Fahrzeug bearbeiten
  * Fahrzeug löschen
* Fahzeugtypverwaltung
  * Fahrzeugtyp anlegen
  * Fahrzeugtyp bearbeiten
  * Fahrzeugtyp löschen
* Laderaumauslastung
  * Fahrzeug beladen
  * Fahrzeug entladen
  * Ware umladen (extra Ansicht?)
  * Fahrzeugstatus anpassen
  * Liste sortieren (Auslastung, freie Kapazität)
  * Liste filtern (volle Fahrzeuge, Fahrzeug unterwegs, Fahrzeug defekt)


### Web-Ansicht
