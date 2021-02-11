import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { TokenService } from '../servizi/token.service';

@Injectable({
  providedIn: 'root'
})
export class AutenticatoGuard implements CanActivate {
  constructor(private tokenService: TokenService, private router: Router) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    const loggedIn = route.data.loggedIn; 
    if(loggedIn){
      if (!this.tokenService.isLogged()) {
        this.router.navigate(["/forbidden"])
        return false;
      }
      return true;
    }else{
      if (this.tokenService.isLogged()) {
        this.router.navigate(["/forbidden"])
        return false;
      }
      return true;
    }    
  }
  
}
