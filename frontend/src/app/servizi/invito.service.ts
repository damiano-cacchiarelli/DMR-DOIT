import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Invito } from '../modello/invito/invito';
import { InvitoDto } from '../modello-dto/invito-dto/invito-dto';
import { RispostaInvitoDto } from '../modello-dto/invito-dto/risposta-invito-dto';

@Injectable({
  providedIn: 'root'
})
export class InvitoService {

  invitoURL = environment.invitoURL;

  constructor(private httpClient: HttpClient) { }

  public invia(invito: InvitoDto): Observable<any> {
    return this.httpClient.post<any>(this.invitoURL + "/invia", invito);
  }

  public getInvito(id: string): Observable<Invito> {
    return this.httpClient.get<Invito>(this.invitoURL + "/" + id);
  }

  public getAll(): Observable<Invito[]> {
    return this.httpClient.get<Invito[]>(this.invitoURL + "/all");
  }

  public gestisci(rispostaInvito: RispostaInvitoDto): Observable<any> {
    return this.httpClient.put<any>(this.invitoURL + "/gestisci", rispostaInvito);
  }
}
