import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { RuoloDto } from '../modello-dto/iscritto-dto/ruolo/ruolo-dto';
import { TipologiaRuolo } from '../modello/iscritto/ruolo/tipologia-ruolo.enum';

@Injectable({
  providedIn: 'root'
})
export class IscrittoService {

  private iscrittoURL = environment.iscrittoURL;

  constructor(private httpClient: HttpClient) { }

  public aggiungiRuolo(ruolo: RuoloDto): Observable<any>{
    return this.httpClient.put<any>(this.iscrittoURL + "/aggiungi_ruolo", ruolo);
  }

  public ruoliDisponibili(): Observable<TipologiaRuolo[]> {
    return this.httpClient.get<TipologiaRuolo[]>(this.iscrittoURL + "/ruoli_disponibili");
  }
}
