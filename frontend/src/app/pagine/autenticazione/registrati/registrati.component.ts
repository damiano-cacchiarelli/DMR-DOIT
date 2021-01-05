import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Ente } from 'src/app/modello/iscritto/ente';
import { Iscritto } from 'src/app/modello/iscritto/iscritto';
import { Persona } from 'src/app/modello/iscritto/persona';
import { AutenticazioneService } from 'src/app/servizi/autenticazione.service';
import { TokenService } from 'src/app/servizi/token.service';

@Component({
  selector: 'app-registrati',
  templateUrl: './registrati.component.html',
  styleUrls: ['./registrati.component.css']
})
export class RegistratiComponent implements OnInit {

  identificativo: string = null as any;
  email: string = null as any;
  password: string = null as any;

  tipo: string = "persona";

  nome: string = null as any;
  cognome: string = null as any;
  cittadinanza: string = null as any;
  sesso: string = null as any;
  telefono: string = "";

  sede: string = null as any;
  annoDiFondazione: Date = null as any;

  constructor(
    private tokenService: TokenService,
    private autenticazioneService: AutenticazioneService,
    private toastr: ToastrService,
    private router: Router) { }

  ngOnInit(): void {
  }

  onRegistrati(): void {
    let iscritto: Iscritto;
    if (this.tipo === "persona") {
      iscritto = new Persona(this.identificativo, this.email, this.password, this.nome, this.cognome, this.cittadinanza, this.sesso, this.telefono);
    } else {
      iscritto = new Ente(this.identificativo, this.email, this.password, this.sede, this.annoDiFondazione);
    }
    iscritto.tipo = this.tipo;

    this.autenticazioneService.registra(iscritto).subscribe(
      data => {
        console.log(data);
        this.toastr.success("Registrato con successo!", "OK", {
          timeOut: 3000, positionClass: "toast-top-center"
        });
        this.router.navigate(["/accedi"]);
      },
      err => {
        this.toastr.error(err.error.messaggio, "Errore", {
          timeOut: 3000, positionClass: "toast-top-center"
        });
      });
  }

}