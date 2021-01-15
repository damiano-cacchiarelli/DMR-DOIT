import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { RispostaInvitoDto } from '../modello/invito/risposta-invito-dto';
import { IscrittoDto } from '../modello/iscritto/iscritto-dto';
import { ProgettoDto } from '../modello/progetto/progetto-dto';import { Tag } from '../modello/progetto/tag';
import { TagListDto } from '../modello/progetto/tag-list-dto';

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

  public gestisciRichiestaPartecipazione(rispostaInvito: RispostaInvitoDto): Observable<any> {
    throw new Error('Method not implemented.');
  }
}
