import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { EnteDto } from 'src/app/modello-dto/iscritto-dto/ente-dto';
import { IscrittoDto } from 'src/app/modello-dto/iscritto-dto/iscritto-dto';
import { PersonaDto } from 'src/app/modello-dto/iscritto-dto/persona-dto';
import { VisitatoreService } from 'src/app/servizi/visitatore.service';

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
    private autenticazioneService: VisitatoreService,
    private toastr: ToastrService,
    private router: Router) { }

  ngOnInit(): void {
  }

  onRegistrati(): void {
    let iscritto: IscrittoDto;
    if (this.tipo === "persona") {
      iscritto = new PersonaDto(this.identificativo, this.email, this.password, this.nome, this.cognome, this.cittadinanza, this.sesso, this.telefono);
    } else {
      iscritto = new EnteDto(this.identificativo, this.email, this.password, this.sede, this.annoDiFondazione);
    }
    iscritto.tipo = this.tipo;

    this.autenticazioneService.registra(iscritto).subscribe(
      data => {
        this.toastr.success("Registrato con successo!", "OK", {
          timeOut: 3000, positionClass: "toast-top-center"
        });
        this.router.navigate(["/accedi"]);
      },
      err => {
        this.toastr.error(err.error.messaggio, "Errore", {
          timeOut: 3000, positionClass: "toast-top-center"
        });
        console.log(err);
      });
  }

}
