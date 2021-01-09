import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Progetto } from '../modello/progetto/progetto';

@Injectable({
  providedIn: 'root'
})
export class ProponenteService {
  private proponenteURL = environment.proponenteURL;

  constructor(private httpClient: HttpClient) { }

  public proponi(progetto: Progetto): Observable<any> {
    return this.httpClient.post<any>(this.proponenteURL + "/proponi", progetto);
  }
}