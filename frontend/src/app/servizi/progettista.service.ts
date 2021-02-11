import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { InvitoDto } from '../modello-dto/invito-dto/invito-dto';
import { RispostaInvitoDto } from '../modello-dto/invito-dto/risposta-invito-dto';

@Injectable({
  providedIn: 'root'
})
export class ProgettistaService {
  private progettistaURL = environment.progettistaURL;

  constructor(private httpClient: HttpClient) { }

  public candidati(invitoDto: InvitoDto): Observable<any> {
    return this.httpClient.put<any>(this.progettistaURL + "/candidati", invitoDto);
  }

  public gestisciPropostaPartecipazione(risposta: RispostaInvitoDto): Observable<any>{
    return this.httpClient.put<any>(this.progettistaURL + "/gestisci_proposta_partecipazione", risposta);
  }
}
