import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Iscritto } from '../modello/iscritto/iscritto';
import { JwtDto } from '../modello/jwt-dto';
import { LoginIscritto } from '../modello/login-iscritto';

@Injectable({
  providedIn: 'root'
})
export class AutenticazioneService {

  private autenticazioneURL = environment.autenticazioneURL;

  constructor(private httpClient: HttpClient) { }

  public registra(iscritto: Iscritto): Observable<any> {
    return this.httpClient.post<any>(this.autenticazioneURL + iscritto.tipo + "/crea", iscritto);
  }

  public accedi(loginIscritto: LoginIscritto): Observable<JwtDto> {
    return this.httpClient.post<JwtDto>(this.autenticazioneURL + "accedi", loginIscritto);
  }
}
