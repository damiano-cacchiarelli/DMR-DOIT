import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AutenticatoGuard } from './guardie/autenticato.guard';
import { RuoloGuard } from './guardie/ruolo.guard';
import { AccediComponent } from './pagine/autenticazione/accedi/accedi.component';
import { RegistratiComponent } from './pagine/autenticazione/registrati/registrati.component';
import { HomeComponent } from './pagine/home/home.component';
import { AggiungiRuoloComponent } from './pagine/iscritto/aggiungi-ruolo/aggiungi-ruolo.component';
import { DettagliComponent } from './pagine/progetto/dettagli/dettagli.component';
import { VetrinaProgettiComponent } from './pagine/progetto/vetrina-progetti/vetrina-progetti.component';
import { ProponiComponent } from './pagine/ruoli/proponente/proponi/proponi.component';

const routes: Routes = [
  {path: "", component: HomeComponent},
  {path: "accedi", component: AccediComponent, canActivate: [!AutenticatoGuard]},
  {path: "registrati", component: RegistratiComponent, canActivate: [!AutenticatoGuard]},
  {path: "vetrina", component: VetrinaProgettiComponent},
  {path: "aggiungi-ruolo", component: AggiungiRuoloComponent, canActivate: [AutenticatoGuard]},
  {path: "dettagli-progetto/:id", component: DettagliComponent},

  {path: "proponente/proponi", component: ProponiComponent, canActivate: [RuoloGuard], data: { ruoloAspettato:['ROLE_PROPONENTE'] }},
  // Esempio su come utilizzare la guardia RuoloGurad
  {path: "test", component: RegistratiComponent, canActivate: [RuoloGuard], data: { ruoloAspettato:['ROLE_PROPONENTE', 'ROLE_PROGETTISTA'] }},
    
  {path: "**", redirectTo: "", pathMatch: "full"}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
