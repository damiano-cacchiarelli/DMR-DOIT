import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

import { interceptorProvider } from './intercettori/token.interceptor';

import { HomeComponent } from './pagine/home/home.component';
import { MenuComponent } from './pagine/menu/menu.component';
import { AccediComponent } from './pagine/autenticazione/accedi/accedi.component';
import { RegistratiComponent } from './pagine/autenticazione/registrati/registrati.component';

// Per usare ngx-toastr
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastrModule } from 'ngx-toastr';
import { AggiungiRuoloComponent } from './pagine/iscritto/aggiungi-ruolo/aggiungi-ruolo.component';
import { VetrinaProgettiComponent } from './pagine/progetto/vetrina-progetti/vetrina-progetti.component';
import { DettagliComponent } from './pagine/progetto/dettagli/dettagli.component';
import { ProponiComponent } from './pagine/ruoli/proponente/proponi/proponi.component';

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
    ProponiComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot(),
  ],
  providers: [interceptorProvider],
  bootstrap: [AppComponent]
})
export class AppModule { }
