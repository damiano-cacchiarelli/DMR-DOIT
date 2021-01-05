import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { TokenService } from '../servizi/token.service';

@Injectable({
  providedIn: 'root'
})
export class RuoloGuard implements CanActivate {

  constructor(private tokenService: TokenService, private router: Router) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    const ruoloAspettato = route.data.ruoloAspettato; //ruoli necessari per utilizzare la risorsa (uno dei tanti basta)
    const ruoli = this.tokenService.getRuoli(); //ruoli in possesso
    let haRuolo = false;
    ruoloAspettato.forEach((ruolo: string) => {
      if (ruoli.indexOf(ruolo) !== -1) {
        // ha un ruolo tra quelli necessari
        haRuolo = true;
      }
    });
    if (!this.tokenService.getToken() || !haRuolo) {
      this.router.navigate(["/"])
      return false;
    }
    return true;
  }
}
