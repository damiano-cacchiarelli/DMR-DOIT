import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

const TOKEN_KEY = 'AuthToken';

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  constructor(private ruoter: Router) { }

  public setToken(token: string): void {
    window.localStorage.removeItem(TOKEN_KEY);
    window.localStorage.setItem(TOKEN_KEY, token);
  }

  public getToken(): string | null {
    return localStorage.getItem(TOKEN_KEY);
  }

  public isLogged(): boolean {
    if (this.getToken())
      return true;
    return false;
  }

  public getIdentificativo(): string | null {
    if (!this.isLogged()) return null;
    return this.getValoriToken().sub;
  }

  public getRuoli(): string[] {
    if (!this.isLogged()) return [];
    return this.getValoriToken().ruoli;
  }

  public hasRuolo(ruolo: string): boolean {
    return this.getRuoli().includes(ruolo);
  }

  public disconnetti(): void {
    window.localStorage.clear();
    this.ruoter.navigate(['/accedi']);
  }

  private getValoriToken(): any {
    // il token ha questa forma: eyasmkasjkdg.asjdhgasdh.fashgfsajg
    // il contenuto centrale è il payload
    const payload = this.getToken()?.split('.')[1] as string;
    const payloadDecodificato = atob(payload);
    return JSON.parse(payloadDecodificato);
  }
}
