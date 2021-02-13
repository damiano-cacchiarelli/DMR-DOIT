import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { MatStepperModule } from '@angular/material/stepper';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MatIconModule } from '@angular/material/icon';

import { interceptorProvider } from './intercettori/token.interceptor';
import { HomeComponent } from './pagine/home/home.component';
import { MenuComponent } from './pagine/menu/menu.component';
import { AccediComponent } from './pagine/autenticazione/accedi/accedi.component';
import { RegistratiComponent } from './pagine/autenticazione/registrati/registrati.component';
import { AggiungiRuoloComponent } from './pagine/iscritto/aggiungi-ruolo/aggiungi-ruolo.component';
import { VetrinaProgettiComponent } from './pagine/progetto/vetrina-progetti/vetrina-progetti.component';
import { DettagliComponent } from './pagine/progetto/dettagli/dettagli.component';
import { ProponiComponent } from './pagine/ruoli/proponente/proponi/proponi.component';
import { ProfiloComponent } from './pagine/iscritto/profilo/profilo.component';
import { ProfiloEnteComponent } from './pagine/iscritto/profilo-ente/profilo-ente.component';
import { ProfiloPersonaComponent } from './pagine/iscritto/profilo-persona/profilo-persona.component';
import { BachecaComponent } from './pagine/invito/bacheca/bacheca.component';
import { DettagliInvitoComponent } from './pagine/invito/dettagli-invito/dettagli-invito.component';
import { ValutaProgettoComponent } from './pagine/ruoli/esperto/valuta-progetto/valuta-progetto.component';
import { InvitaProgettistiComponent } from './pagine/ruoli/proponi/invita-progettisti/invita-progettisti.component';
import { PermettiValutazioneComponent } from './pagine/ruoli/proponi/permetti-valutazione/permetti-valutazione.component';
import { SceltaTagComponent } from './pagine/progetto/vetrina-progetti/scelta-tag/scelta-tag.component';
import { ForbiddenComponent } from './pagine/error/forbidden/forbidden.component';
import { NotFoundComponent } from './pagine/error/not-found/not-found.component';

// Per usare ngx-toastr
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastrModule } from 'ngx-toastr';
import { loaderInterceptorProvider } from './intercettori/loader.interceptor';


@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    MenuComponent,
    AccediComponent,
    RegistratiComponent,
    AggiungiRuoloComponent,
    VetrinaProgettiComponent,
    DettagliComponent,
    ProponiComponent,
    ProfiloComponent,
    ProfiloEnteComponent,
    ProfiloPersonaComponent,
    BachecaComponent,
    DettagliInvitoComponent,
    ValutaProgettoComponent,
    InvitaProgettistiComponent,
    PermettiValutazioneComponent,
    SceltaTagComponent,
    ForbiddenComponent,
    NotFoundComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MatStepperModule,
    MatIconModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot(),
  ],
  providers: [interceptorProvider, loaderInterceptorProvider],
  bootstrap: [AppComponent]
})
export class AppModule { }
