import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Progetto } from '../modello/progetto/progetto';

@Injectable({
  providedIn: 'root'
})
export class ProgettoService {

  private progettoURL = environment.progettoURL;
  constructor(private httpClient: HttpClient) {  }

  
  public vetrinaProgetti(): Observable<Progetto[]> {
    return this.httpClient.get<Progetto[]>(this.progettoURL+ "/vetrina");
  }

  public getProgetto(id: string){
    return this.httpClient.get<Progetto>(this.progettoURL+ "/" + id);
  }
}
