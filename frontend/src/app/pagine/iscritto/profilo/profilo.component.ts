import { Component, Directive, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { EnteDto } from 'src/app/modello/iscritto/ente-dto';
import { Iscritto } from 'src/app/modello/iscritto/iscritto';
import { PersonaDto } from 'src/app/modello/iscritto/persona-dto';
import { RuoloOpzioni } from 'src/app/modello/iscritto/ruolo-opzioni';
import { TipologiaRuolo } from 'src/app/modello/iscritto/tipologia-ruolo.enum';
import { VisitatoreService } from 'src/app/servizi/visitatore.service';
import { ProfiloEnteComponent } from '../profilo-ente/profilo-ente.component';
import { ProfiloPersonaComponent } from '../profilo-persona/profilo-persona.component'

@Component({
  selector: 'app-profilo',
  templateUrl: './profilo.component.html',
  styleUrls: ['./profilo.component.css'],
})
export class ProfiloComponent implements OnInit {

  @Input() identificativo?: string;
  @Input() valutazione = false;

  iscritto?: Iscritto;
  persona?: PersonaDto;
  ente?: EnteDto;

  TipologiaRuolo = TipologiaRuolo;

  constructor(private visitatoreService: VisitatoreService, private activatedRoute: ActivatedRoute,
    private toastr: ToastrService) {

  }

  ngOnInit(): void {
    let id: string = "";
    if (!this.valutazione)
      id = this.activatedRoute.snapshot.params.id;
    else if (this.identificativo)
      id = this.identificativo;

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

  hasRuoli(): boolean {
    return this.iscritto?.ruoli.length != 0;
  }

  getColore(ruolo: TipologiaRuolo): string {
    const ro = RuoloOpzioni.RUOLI.get(ruolo);
    if (ro)
      return ro.colore;
    return "table-primary";
  }
}
