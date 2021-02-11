import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AutenticatoGuard } from './guardie/autenticato.guard';
import { Data } from './guardie/data';
import { RuoloGuard } from './guardie/ruolo.guard';
import { TipologiaRuolo } from './modello/iscritto/ruolo/tipologia-ruolo.enum';
import { AccediComponent } from './pagine/autenticazione/accedi/accedi.component';
import { RegistratiComponent } from './pagine/autenticazione/registrati/registrati.component';
import { ForbiddenComponent } from './pagine/error/forbidden/forbidden.component';
import { NotFoundComponent } from './pagine/error/not-found/not-found.component';
import { HomeComponent } from './pagine/home/home.component';
import { BachecaComponent } from './pagine/invito/bacheca/bacheca.component';
import { DettagliInvitoComponent } from './pagine/invito/dettagli-invito/dettagli-invito.component';
import { AggiungiRuoloComponent } from './pagine/iscritto/aggiungi-ruolo/aggiungi-ruolo.component';
import { ProfiloComponent } from './pagine/iscritto/profilo/profilo.component';
import { DettagliComponent } from './pagine/progetto/dettagli/dettagli.component';
import { VetrinaProgettiComponent } from './pagine/progetto/vetrina-progetti/vetrina-progetti.component';
import { ValutaProgettoComponent } from './pagine/ruoli/esperto/valuta-progetto/valuta-progetto.component';
import { ProponiComponent } from './pagine/ruoli/proponente/proponi/proponi.component';

const routes: Routes = [
  {path: "", component: HomeComponent},
  {path: "accedi", component: AccediComponent, canActivate: [AutenticatoGuard], data: new Data([], false)}, //, canActivate: [!AutenticatoGuard]
  {path: "registrati", component: RegistratiComponent, canActivate: [AutenticatoGuard], data: new Data([], false)}, //, canActivate: [!AutenticatoGuard]
  {path: "vetrina", component: VetrinaProgettiComponent},
  {path: "aggiungi-ruolo", component: AggiungiRuoloComponent, canActivate: [AutenticatoGuard], data: new Data([], true)},
  {path: "dettagli-progetto/:id", component: DettagliComponent},
  {path: "visitatore/:id", component: ProfiloComponent},
  {path: "proponente/proponi", component: ProponiComponent, canActivate: [RuoloGuard], data: new Data([TipologiaRuolo.ROLE_PROPONENTE])},
  {path: "esperto/valuta/:id/:idInvito", component: ValutaProgettoComponent, canActivate: [RuoloGuard], data: new Data([TipologiaRuolo.ROLE_ESPERTO])},
  
  {path: "bacheca", component: BachecaComponent, canActivate: [AutenticatoGuard], data: new Data([], true)},
  {path: "bacheca/:id", component: DettagliInvitoComponent, canActivate: [AutenticatoGuard], data: new Data([], true)},
  {path: "not-found", component: NotFoundComponent},  
  {path: "forbidden", component: ForbiddenComponent}, 

  {path: "**", redirectTo: "not-found", pathMatch: "full"}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
