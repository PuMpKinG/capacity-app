import {Inject, Injectable, Optional} from '@angular/core';

export enum LogLevel {
  TRACE = 0,
  DEBUG = 1,
  INFO = 2,
  WARN = 3,
  ERROR = 4,
}

@Injectable({
  providedIn: 'root',
})
export class LoggerService {
  private enabledLogLevel: LogLevel;

  constructor() {
    this.enabledLogLevel = LogLevel.DEBUG;
    this.info('LoggerService initialized with LogLevel: ', LogLevel[this.enabledLogLevel]);
  }

  trace = (message: any, ...optionalParams: any[]) => {
    this.log(LogLevel.TRACE, message, ...optionalParams);
  };

  debug = (message: any, ...optionalParams: any[]) => {
    this.log(LogLevel.DEBUG, message, ...optionalParams);
  };

  info = (message: any, ...optionalParams: any[]) => {
    this.log(LogLevel.INFO, message, ...optionalParams);
  };

  warn = (message: any, ...optionalParams: any[]) => {
    this.log(LogLevel.WARN, message, ...optionalParams);
  };

  error = (message: any, ...optionalParams: any[]) => {
    this.log(LogLevel.ERROR, message, ...optionalParams);
  };

  private log(logLevel: LogLevel, message: any, ...optionalParams: any[]) {
    if (logLevel < this.enabledLogLevel) {
      return;
    }
    const timestamp = new Date().toTimeString().slice(0, 8) + ' ';
    const level = (LogLevel[logLevel] + '      ').slice(0, 6);

    if (typeof message === 'string') {
      message = timestamp + level + message;
    } else {
      optionalParams.unshift(message); // unshift = add to start
      message = timestamp + level;
    }

    switch (logLevel) {
      case LogLevel.TRACE:
        // eslint-disable-next-line no-console, no-restricted-syntax
        console.trace(message, ...optionalParams);
        break;
      case LogLevel.DEBUG:
        console.log(message, ...optionalParams);
        break;
      case LogLevel.INFO:
        // eslint-disable-next-line no-console, no-restricted-syntax
        console.info(message, ...optionalParams);
        break;
      case LogLevel.WARN:
        console.warn(message, ...optionalParams);
        break;
      case LogLevel.ERROR:
        console.error(message, ...optionalParams);
        break;
      default:
        console.log(message, ...optionalParams);
        break;
    }
  }
}
