import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Iscritto } from '../modello/iscritto/iscritto';
import { ProgettoDto } from '../modello/progetto/progettoDto';import { Tag } from '../modello/progetto/tag';
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

  public espertiConsigliati(tags: TagListDto): Observable<Iscritto[]>{
    return this.httpClient.post<Iscritto[]>(this.proponenteURL + "/esperti_consigliati", tags);
  }
}
