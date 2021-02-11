import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { RispostaInvitoDto } from '../modello-dto/invito-dto/risposta-invito-dto';
import { ValutazioneDto } from '../modello-dto/progetto-dto/valutazione-dto';

@Injectable({
  providedIn: 'root'
})
export class EspertoService {
  private espertoURL = environment.espertoURL;

  constructor(private httpClient: HttpClient) { }

  public valutaProgetto(idInvito: string, valutazione: ValutazioneDto): Observable<any> {
    return this.httpClient.post<any>(this.espertoURL + "/progetto/valuta/" + idInvito, valutazione);
  }

  public rifiutaValutazione(risposta: RispostaInvitoDto): Observable<any> {
    return this.httpClient.put<any>(this.espertoURL + "/rifiuta_richiesta/" + risposta.idInvito, "");
  }
}
