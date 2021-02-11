import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { EnteDto } from 'src/app/modello-dto/iscritto-dto/ente-dto';
import { Iscritto } from 'src/app/modello/iscritto/iscritto';
import { PersonaDto } from 'src/app/modello-dto/iscritto-dto/persona-dto';
import { Ruolo } from 'src/app/modello/iscritto/ruolo/ruolo';
import { RuoloOpzioni } from 'src/app/modello/iscritto/ruolo/ruolo-opzioni';
import { TipologiaRuolo } from 'src/app/modello/iscritto/ruolo/tipologia-ruolo.enum';
import { VisitatoreService } from 'src/app/servizi/visitatore.service';

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

    //this.activatedRoute.snapshot.params.id;
    
    this.activatedRoute.paramMap.subscribe((params: ParamMap) => {
      if (!this.valutazione)
        this.identificativo = params.get('id') as string;
        
      this.updateProfilo();
    });
  }

  public updateProfilo(){
    if(!this.identificativo) return;
    this.visitatoreService.getIscritto(this.identificativo).subscribe(
      data => {
        if (data.nome) {
          this.persona = data;
          this.ente = null as any;
        } else {
          this.ente = data;
          this.persona = null as any;
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

  getRuolo(ruolo: Ruolo): string {
    switch (ruolo.ruolo) {
      case TipologiaRuolo.ROLE_ESPERTO:
        return "ESPERTO";
      case TipologiaRuolo.ROLE_PROGETTISTA:
        return "PROGETTISTA";
      case TipologiaRuolo.ROLE_PROPONENTE:
        return "PROPONENTE";
      case TipologiaRuolo.ROLE_SPONSOR:
        return "SPONSOR";
      default:
        return "";
    }
  }
}
