import { Component, Directive, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Ente } from 'src/app/modello/iscritto/ente';
import { Persona } from 'src/app/modello/iscritto/persona';
import { VisitatoreService } from 'src/app/servizi/visitatore.service';
import { ProfiloEnteComponent } from '../profilo-ente/profilo-ente.component';
import { ProfiloPersonaComponent } from '../profilo-persona/profilo-persona.component'

@Component({
  selector: 'app-profilo',
  templateUrl: './profilo.component.html',
  styleUrls: ['./profilo.component.css'],
})
export class ProfiloComponent implements OnInit {

  persona?: Persona;
  ente?: Ente;
  isPersona?: boolean;
  constructor(private visitatoreService: VisitatoreService, private activatedRoute: ActivatedRoute,
    private toastr: ToastrService) {

  }

  ngOnInit(): void {
    const id = this.activatedRoute.snapshot.params.id;
    this.visitatoreService.getIscritto(id).subscribe(
      data => {
        if (data.nome) {
          this.persona = data;
          this.isPersona = true;
        } else {
          this.ente = data;
          this.isPersona = false;
        }
      },
      err => {
        this.toastr.error(err.error.messaggio, "Errore", {
          timeOut: 3000, positionClass: "toast-bottom-right"
        });
      }
    );
  }
}
