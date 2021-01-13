import { Component, Directive, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { EnteDto } from 'src/app/modello/iscritto/ente-dto';
import { Iscritto } from 'src/app/modello/iscritto/iscritto';
import { PersonaDto } from 'src/app/modello/iscritto/persona-dto';
import { VisitatoreService } from 'src/app/servizi/visitatore.service';
import { ProfiloEnteComponent } from '../profilo-ente/profilo-ente.component';
import { ProfiloPersonaComponent } from '../profilo-persona/profilo-persona.component'

@Component({
  selector: 'app-profilo',
  templateUrl: './profilo.component.html',
  styleUrls: ['./profilo.component.css'],
})
export class ProfiloComponent implements OnInit {

  iscritto?: Iscritto;
  persona?: PersonaDto;
  ente?: EnteDto;
  constructor(private visitatoreService: VisitatoreService, private activatedRoute: ActivatedRoute,
    private toastr: ToastrService) {

  }

  ngOnInit(): void {
    const id = this.activatedRoute.snapshot.params.id;
    this.visitatoreService.getIscritto(id).subscribe(
      data => {
        console.log(data);
        if (data.nome) {
          this.persona = data;
        } else {
          this.ente = data;
        }
        this.iscritto = data;
      },
      err => {
        this.toastr.error(err.error.messaggio, "Errore", {
          timeOut: 3000, positionClass: "toast-bottom-right"
        });
      }
    );
  }
}
