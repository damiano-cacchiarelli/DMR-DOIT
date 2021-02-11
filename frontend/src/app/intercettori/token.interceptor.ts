import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HTTP_INTERCEPTORS,
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { TokenService } from '../servizi/token.service';

@Injectable()
export class TokenInterceptor implements HttpInterceptor {

  constructor(private tokenService: TokenService) { }

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    if (this.tokenService.getToken() != null)
      request = this.addToken(request);

    return next.handle(request);
  }

  private addToken(request: HttpRequest<unknown>): HttpRequest<unknown> {
    return request.clone({
      headers: request.headers.set("Authorization", `Bearer ${this.tokenService.getToken()}`)
    });
  }
}

/* 
L'interceptor per poter esser utilizzato deve essere aggiunto all'array HTTP_INTERCEPTORS. 
In questo modo si dichiara che HTTP_INTERCEPTORS utilizza questa classe.
*/
export const interceptorProvider = [{ provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true }];