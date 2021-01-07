import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AutenticatoGuard } from './guardie/autenticato.guard';
import { NonAutenticatoGuard } from './guardie/non-autenticato.guard';
import { RuoloGuard } from './guardie/ruolo.guard';
import { AccediComponent } from './pagine/autenticazione/accedi/accedi.component';
import { RegistratiComponent } from './pagine/autenticazione/registrati/registrati.component';
import { HomeComponent } from './pagine/home/home.component';
import { AggiungiRuoloComponent } from './pagine/iscritto/aggiungi-ruolo/aggiungi-ruolo.component';

const routes: Routes = [
  {path: "", component: HomeComponent},
  {path: "accedi", component: AccediComponent, canActivate: [NonAutenticatoGuard]},
  {path: "registrati", component: RegistratiComponent, canActivate: [NonAutenticatoGuard]},
  
  {path: "aggiungi-ruolo", component: AggiungiRuoloComponent, canActivate: [AutenticatoGuard]},
  
  // Esempio su come utilizzare la guardia RuoloGurad
  {path: "test", component: RegistratiComponent, canActivate: [RuoloGuard], data: { ruoloAspettato:['ROLE_PROPONENTE', 'ROLE_PROGETTISTA'] }},
    
  {path: "**", redirectTo: "", pathMatch: "full"}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
