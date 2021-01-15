import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { RispostaInvitoDto } from '../modello/invito/risposta-invito-dto';

@Injectable({
  providedIn: 'root'
})
export class ProgettistaService {
  private progettistaURL = environment.progettistaURL;

  constructor(private httpClient: HttpClient) { }

  public candidati(idProgetto: number): Observable<any> {
    return this.httpClient.put<any>(this.progettistaURL + "/candidati/" + idProgetto, "");
  }

  public gestisciRichiestaPartecipazione(risposta: RispostaInvitoDto): Observable<any>{
    return this.httpClient.put<any>(this.progettistaURL + "/gestisci_richiesta_partecipazione", risposta);
  }
}
