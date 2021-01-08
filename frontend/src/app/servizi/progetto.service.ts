import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { ProgettoDettagliato } from '../modello/progetto/progetto-dettagliato';

@Injectable({
  providedIn: 'root'
})
export class ProgettoService {

  private progettoURL = environment.progettoURL;
  constructor(private httpClient: HttpClient) {  }

  
  public vetrinaProgetti(): Observable<ProgettoDettagliato[]> {
    return this.httpClient.get<ProgettoDettagliato[]>(this.progettoURL+ "/vetrina");
  }

  public getProgetto(id: string){
    return this.httpClient.get<ProgettoDettagliato>(this.progettoURL+ "/" + id);
  }
}
