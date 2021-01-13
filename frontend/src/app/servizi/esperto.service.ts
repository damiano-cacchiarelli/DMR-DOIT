import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { ValutazioneDto } from '../modello/progetto/valutazione-dto';

@Injectable({
  providedIn: 'root'
})
export class EspertoService {
  private espertoURL = environment.espertoURL;

  constructor(private httpClient: HttpClient) { }

  public valutaProgetto(idProgetto: number, valutazione: ValutazioneDto): Observable<any> {
    return this.httpClient.post<any>(this.espertoURL + "/progetto/valuta/" + idProgetto, valutazione);
  }
}
