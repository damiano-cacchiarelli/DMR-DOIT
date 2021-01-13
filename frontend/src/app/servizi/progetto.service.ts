import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Progetto } from '../modello/progetto/progetto';
import { TagListDto } from '../modello/progetto/tag-list-dto';

@Injectable({
  providedIn: 'root'
})
export class ProgettoService {

  private progettoURL = environment.progettoURL;
  constructor(private httpClient: HttpClient) {  }

  
  public vetrinaProgetti(): Observable<Progetto[]> {
    return this.httpClient.get<Progetto[]>(this.progettoURL+ "/vetrina");
  }

  public getProgetto(id: number){
    return this.httpClient.get<Progetto>(this.progettoURL+ "/" + id);
  }

  public cercaProgetto(nome: string, tags: TagListDto){
    return this.httpClient.post<Progetto[]>(this.progettoURL+ "/ricerca/" + nome, tags);
  }
}
