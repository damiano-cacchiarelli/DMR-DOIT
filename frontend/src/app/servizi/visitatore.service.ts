import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { IscrittoDto } from '../modello/iscritto/iscritto-dto';
import { TipologiaRuolo } from '../modello/iscritto/tipologia-ruolo.enum';
import { JwtDto } from '../modello/jwt-dto';
import { LoginIscritto } from '../modello/login-iscritto';

@Injectable({
  providedIn: 'root'
})
export class VisitatoreService {

  private autenticazioneURL = environment.autenticazioneURL;

  constructor(private httpClient: HttpClient) { }

  public registra(iscritto: IscrittoDto): Observable<any> {
    return this.httpClient.post<any>(this.autenticazioneURL + "/" + iscritto.tipo + "/crea", iscritto);
  }

  public accedi(loginIscritto: LoginIscritto): Observable<JwtDto> {
    return this.httpClient.post<JwtDto>(this.autenticazioneURL + "/accedi", loginIscritto);
  }

  public getIscritto(id: string): Observable<any> {
    return this.httpClient.get<any>(this.autenticazioneURL + "/" + id);
  }

  public getIscrittoByRuolo(id: string, ruolo: TipologiaRuolo): Observable<any> {
    return this.httpClient.get<any>(this.autenticazioneURL + "/esiste/" + id + "/" + ruolo);
  }
}
