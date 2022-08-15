import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders} from '@angular/common/http';
import {catchError, finalize, Observable, tap, throwError as _throw} from 'rxjs';
import {LoggerService} from "./logger.service";

@Injectable({
    providedIn: 'root'
})
export class HttpService {

    constructor(private http: HttpClient, private log: LoggerService) {
    }

    get<ResponseType>(
        serviceUrl: string,
        additionalOptions: any = {}
    ): Observable<ResponseType> {
        return this.request('GET', serviceUrl, null, additionalOptions);
    }

    delete<ResponseType>(serviceUrl: string, request?: any): Observable<ResponseType> {
        return this.request('DELETE', serviceUrl, request);
    }

    post<RequestType, ResponseType>(
        request: RequestType,
        serviceUrl: string,
    ): Observable<ResponseType> {
        return this.request('POST', serviceUrl, request);
    }

    put<RequestType, ResponseType>(
        request: RequestType,
        serviceUrl: string,
    ): Observable<ResponseType> {
        return this.request('PUT', serviceUrl, request);
    }

    private request<RequestType, ResponseType>(
        method: string,
        serviceUrl: string,
        request?: RequestType,
        additionalOptions: any = {}
    ): Observable<ResponseType> {

        let headers = new HttpHeaders().set('Content-Type', 'application/json');
        const options = {...additionalOptions, body: request, headers: headers};
        return this.http.request<ResponseType>(method, serviceUrl, options).pipe(
            tap(this.log.debug),
            catchError((error) => this.handleError(error, method, serviceUrl)),
            finalize(() => {
            })
        );
    }

    private handleError = (error: HttpErrorResponse, method: string, serviceUrl: string): Observable<HttpErrorResponse> => {
        switch (error.status) {
            case 500:
                this.log.error(error);
                break;

            default:
                this.log.error(error);
                break;
        }

        this.log.error(error);
        return _throw(error);
    };
}
