import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Tag } from '../modello/progetto/tag';

@Injectable({
  providedIn: 'root'
})
export class TagService {
  private tagURL = environment.tagURL;

  constructor(private httpClient: HttpClient) { }

  public tuttiITag(): Observable<Tag[]>{
    return this.httpClient.get<Tag[]>(this.tagURL + "/all");
  }
}
