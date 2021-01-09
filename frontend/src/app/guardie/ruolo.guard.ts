import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { TipologiaRuolo } from '../modello/iscritto/tipologia-ruolo.enum';
import { TokenService } from '../servizi/token.service';
import { AutenticatoGuard } from './autenticato.guard';
import { Data } from './data';

@Injectable({
  providedIn: 'root'
})
export class RuoloGuard implements CanActivate {

  constructor(
    private tokenService: TokenService,
    private router: Router,
    private autenticatoGuard: AutenticatoGuard
  ) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    if (!this.autenticatoGuard.canActivate(route, state)){
      this.router.navigate(["/"]);
      return false;
    }

    const ruoloAspettato: TipologiaRuolo[] = route.data.ruoliNecessari; //ruoli necessari per utilizzare la risorsa (uno dei tanti basta)
    const ruoli = this.tokenService.getRuoli(); //ruoli in possesso
    let haRuolo = false;
    ruoloAspettato.forEach((ruolo: string) => {
      if (ruoli.indexOf(ruolo) !== -1) {
        // ha un ruolo tra quelli necessari
        haRuolo = true;
      }
    });
    if (haRuolo) return true;

    this.router.navigate(["/"])
    return false;
    /*
    if (!this.tokenService.getToken() || !haRuolo) {
      this.router.navigate(["/"])
      return false;
    }
    return true;*/
  }
}
