import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginGuard } from './guardie/login.guard';
import { RuoloGuard } from './guardie/ruolo.guard';
import { AccediComponent } from './pagine/autenticazione/accedi/accedi.component';
import { RegistratiComponent } from './pagine/autenticazione/registrati/registrati.component';
import { HomeComponent } from './pagine/home/home.component';

const routes: Routes = [
  {path: "", component: HomeComponent},
  {path: "accedi", component: AccediComponent, canActivate: [LoginGuard]},
  {path: "registrati", component: RegistratiComponent, canActivate: [LoginGuard]},
  // Esempio su come utilizzare la guardia RuoloGurad
  {path: "test", component: RegistratiComponent, canActivate: [RuoloGuard], data: { ruoloAspettato:['ROLE_PROPONENTE', 'ROLE_PROGETTISTA'] }},
  {path: "**", redirectTo: "", pathMatch: "full"}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
