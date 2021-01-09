import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Ruolo } from '../modello/iscritto/ruolo';
import { TipologiaRuolo } from '../modello/iscritto/tipologia-ruolo.enum';

@Injectable({
  providedIn: 'root'
})
export class IscrittoService {

  private iscrittoURL = environment.iscrittoURL;

  constructor(private httpClient: HttpClient) { }

  public aggiungiRuolo(ruolo: Ruolo): Observable<any>{
    return this.httpClient.put<any>(this.iscrittoURL + "/aggiungi_ruolo", ruolo);
  }

  public ruoliDisponibili(): Observable<TipologiaRuolo[]> {
    return this.httpClient.get<TipologiaRuolo[]>(this.iscrittoURL + "/ruoli_disponibili");
  }
}
