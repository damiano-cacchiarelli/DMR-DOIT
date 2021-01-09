import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { ProgettoDto } from '../modello/progetto/progettoDto';

@Injectable({
  providedIn: 'root'
})
export class ProponenteService {
  private proponenteURL = environment.proponenteURL;

  constructor(private httpClient: HttpClient) { }

  public proponi(progetto: ProgettoDto): Observable<any> {
    return this.httpClient.post<any>(this.proponenteURL + "/proponi", progetto);
  }
}
