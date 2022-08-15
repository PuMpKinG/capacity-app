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

### Datenbank Modelle

Vehicle
* id
* license
* loadingTime
* unloadingTime
* usedCapacity (ldm)
* vehicleStatus (enum)
* vehicleTypeRef

VehicleType
* id
* company
* model
* height
* width
* length
* capacity (transient / ldm)
  -> Im Internet beschrieben, ist die maximale ldm immer die Länge des Fahrzeugs


### Funktionen
* Fahrzeug Verwaltung 
  * Fahrzeug anlegen
  * Fahrzeug bearbeiten
  * Fahrzeug löschen
  
* Fahrzeug-Bestand - Verwaltung
  * Neues Fahrzeug im Bestand
  * Fahrzeug aus dem Bestand löschen

* Fahrzeug-Bestand - Laderaumauslastung
  * Fahrzeug beladen
  * Fahrzeug entladen
  * Ware umladen (extra Ansicht?)
  * Fahrzeugstatus anpassen
  
* Filter / Sortierung
  * Liste sortieren (Auslastung, freie Kapazität)
  * Liste filtern (volle Fahrzeuge, Fahrzeug unterwegs, Fahrzeug defekt)


### Web-Ansicht


## Fragen
* Muss das @Transactional immer an jeder Methode geschrieben werden, oder gibt es eine andere Lösung?
* Autowired: am Constructor oder an den Variablen? Mir gefällt die Schreibweise mit dem Kontruktor - Variablen sind final
* Repository ist ein Interface. Gibt es Fälle für eine Default-Implementierung oder nutzt man immer direkt das Interface wie in meinem Beispiel?
* VehicleUsageRepository: Wie kann ich in den Filter ein Standard-Wert übergeben (Problem Interface)
