import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { InvitoDto } from '../modello-dto/invito-dto/invito-dto';
import { RispostaInvitoDto } from '../modello-dto/invito-dto/risposta-invito-dto';
import { IscrittoDto } from '../modello-dto/iscritto-dto/iscritto-dto';
import { ProgettoDto } from '../modello-dto/progetto-dto/progetto-dto';import { Tag } from '../modello/progetto/tag';
import { TagListDto } from '../modello-dto/progetto-dto/tag-list-dto';

@Injectable({
  providedIn: 'root'
})
export class ProponenteService {

  private proponenteURL = environment.proponenteURL;

  constructor(private httpClient: HttpClient) { }

  public proponi(progetto: ProgettoDto): Observable<any> {
    return this.httpClient.post<any>(this.proponenteURL + "/proponi", progetto);
  }

  public espertiConsigliati(tags: TagListDto): Observable<IscrittoDto[]>{
    return this.httpClient.post<IscrittoDto[]>(this.proponenteURL + "/esperti_consigliati", tags);
  }

  public chiudiCandidature(id: number): Observable<any> {
    return this.httpClient.put<any>(this.proponenteURL + "/chiudi_candidature/" + id, "");
  }

  public faseSuccessiva(id: number): Observable<any> {
    return this.httpClient.put<any>(this.proponenteURL + "/fase_successiva/" + id, "");
  }

  public permettiValutazione(invito:InvitoDto): Observable<any> {
    return this.httpClient.post<any>(this.proponenteURL + "/permetti_valutazione" , invito);
  }

  public selezionaCandidati(rispostaInvito: RispostaInvitoDto): Observable<any> {
    return this.httpClient.post<any>(this.proponenteURL + "/seleziona_candidati" , rispostaInvito);
  }

  public invitaProgettista(invito: InvitoDto): Observable<any> {
    return this.httpClient.post<any>(this.proponenteURL + "/invita_candidati" , invito);
  }
}
