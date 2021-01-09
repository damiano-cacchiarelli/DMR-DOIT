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

// Per usare ngx-toastr
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastrModule } from 'ngx-toastr';
import { AnteprimaComponent } from './pagine/progetto/anteprima/anteprima.component';
import { EnteComponent } from './pagine/profilo/ente/ente.component';
import { PersonaComponent } from './pagine/profilo/persona/persona.component';

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
    AnteprimaComponent,
    EnteComponent,
    PersonaComponent
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
  providers: [interceptorProvider],
  bootstrap: [AppComponent]
})
export class AppModule { }
