import {Injectable} from '@angular/core';
import {LOCALSTORAGE_WAKEUP_SETTING} from '../app.module';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {Observable, of, throwError} from 'rxjs';
import {catchError, tap} from 'rxjs/operators';
import {Esp32Response} from '../app.types';

const HTTP_URL = 'http://';

@Injectable({
  providedIn: 'root'
})
export class HttpService {
  ipAdress: string;
  esp32Info: Esp32Response;
  connected: boolean;

  // values
  brightness: number;
  wakeupLightDuration: number;
  goodNightLightDuration: number;
  // LED_COLORS...

  constructor(private http: HttpClient) {
    const settings = JSON.parse(localStorage.getItem(LOCALSTORAGE_WAKEUP_SETTING));
    this.ipAdress = settings ? settings.ipAdress : null;
  }

  checkConnection(): Observable<Esp32Response> {
    return this.http.get<Esp32Response>(HTTP_URL + this.ipAdress + '/connect')
      .pipe(
        tap(this.setValuesAfterServerConnected),
        catchError(this.handleError)
      );
  }

  toggleAlarm(switchOn: boolean) {
    const param = switchOn ? '1' : '0';
    return this.http.get(HTTP_URL + this.ipAdress + '/toggle-alarm?param=' + param);
  }

  setAlarmTime(alarmTime: string) {
    return this.http.get(HTTP_URL + this.ipAdress + '/set-alarm-time?param=' + alarmTime);
  }

  // set light to color or off
  setLight(hexColor: string, off?: boolean) {
    if (off) {
      hexColor = 'off';
    } else {
      hexColor = hexColor.replace('#', '');
    }

    return this.http.get(HTTP_URL + this.ipAdress + '/set-light?param=' + hexColor);
  }

  setBrightness(brightness: number) {
    brightness = Math.round(brightness / 100 * 255);
    return this.http.get(HTTP_URL + this.ipAdress + '/set-brightness?param=' + brightness);
  }

  setValuesAfterServerConnected = (response: Esp32Response) => {
    this.esp32Info = response;
    this.esp32Info.return_value = null;
    this.brightness = response.return_value * 255 * 100;
    this.connected = true;
  }

  getEsp32InfoString(): string {
    if (this.esp32Info) {
      return 'ID: ' + this.esp32Info.id + ', name: '
        + this.esp32Info.name + ', hardware: '
        + this.esp32Info.hardware + ', connected: '
        + this.esp32Info.connected;
    }
    return null;
  }

  private handleError = (error: HttpErrorResponse) => {
    if (error.error instanceof ErrorEvent) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error('An error occurred:', error.error.message);
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong,
      console.error(
        `Backend returned code ${error.status}, ` +
        `body was: ${error.error}`);

      if (error.status === 0) {
        this.esp32Info = null;
      }
    }
    // return an observable with a user-facing error message
    return throwError(
      'Something bad happened; please try again later.');
  };
}
