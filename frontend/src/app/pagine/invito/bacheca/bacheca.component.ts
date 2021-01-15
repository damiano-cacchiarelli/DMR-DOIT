import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { Observable } from 'rxjs';
import { Invito } from 'src/app/modello/invito/invito';
import { RispostaInvitoDto } from 'src/app/modello/invito/risposta-invito-dto';
import { RuoloSoggetto } from 'src/app/modello/invito/ruolo-soggetto.enum';
import { TipologiaInvito } from 'src/app/modello/invito/tipologia-invito.enum';
import { TipologiaRisposta } from 'src/app/modello/invito/tipologia-risposta.enum';
import { EspertoService } from 'src/app/servizi/esperto.service';
import { InvitoService } from 'src/app/servizi/invito.service';
import { ProgettistaService } from 'src/app/servizi/progettista.service';
import { ProponenteService } from 'src/app/servizi/proponente.service';

@Component({
  selector: 'app-bacheca',
  templateUrl: './bacheca.component.html',
  styleUrls: ['./bacheca.component.css']
})
export class BachecaComponent implements OnInit {

  private inviti: Invito[] = [];
  messaggi: Invito[] = [];

  TipologiaInvito = TipologiaInvito;
  TipologiaRisposta = TipologiaRisposta;

  cercaMessaggio: string = "";

  constructor(
    private invitoService: InvitoService,
    private progettistaService: ProgettistaService,
    private proponenteService: ProponenteService,
    private espertoService: EspertoService,
    private toastr: ToastrService) { }

  ngOnInit(): void {
    this.ottieniTuttiIMessaggi();
  }

  ottieniTuttiIMessaggi(): void {
    this.inviti = [];
    this.messaggi = [];
    this.invitoService.getAll().subscribe(
      data => {
        this.inviti = data;
        this.tuttiIMessaggi();
      },
      err => {
        console.log(err);
      }
    );
  }

  onCercaMessaggio(): void {
    if (this.cercaMessaggio.length == 0) {
      this.tuttiIMessaggi();
      return;
    }
    this.messaggi = [];
    const mess = this.cercaMessaggio.toLocaleLowerCase();
    this.inviti.forEach(i => {
      if (i.contenuto.toLocaleLowerCase().includes(mess)
        || i.nomeProgetto.toLocaleLowerCase().includes(mess))
        this.messaggi.push(i);
    });
  }

  tuttiIMessaggi(): void {
    this.messaggi = [];
    this.messaggi = this.inviti;
  }

  messaggiInviati(): void {
    this.messaggi = [];
    this.inviti.forEach(i => {
      if (i.soggetto == RuoloSoggetto.MITTENTE)
        this.messaggi.push(i);
    });
  }

  messaggiRicevuti(): void {
    this.messaggi = [];
    this.inviti.forEach(i => {
      if (i.soggetto == RuoloSoggetto.DESTINATARIO)
        this.messaggi.push(i);
    });
  }

  onElimina(id: string): void {
    console.log("NON IMPLEMENATTO: Elimina invito ", id);
  }

  onAccetta(id: string): void {
    this.gestisci(new RispostaInvitoDto(id, TipologiaRisposta.ACCETTATA));
  }

  onRifiuta(id: string): void {
    this.gestisci(new RispostaInvitoDto(id, TipologiaRisposta.RIFIUTATA));
  }

  private gestisci(rispostaInvito: RispostaInvitoDto): void {
    let i: Invito = null as any;
    this.inviti.forEach(invi => { if (invi.id == rispostaInvito.idInvito) i = invi; });

    switch (i.tipologiaInvito) {
      case TipologiaInvito.PROPOSTA:
        this.responseSub(this.progettistaService.gestisciRichiestaPartecipazione(rispostaInvito));
        break;
      case TipologiaInvito.VALUTAZIONE:
        this.responseSub(this.invitoService.gestisci(rispostaInvito));
        break;
      case TipologiaInvito.RICHIESTA:
        this.responseSub(this.proponenteService.gestisciRichiestaPartecipazione(rispostaInvito));
        break;
      default:
        break;
    }
  }

  private responseSub(resp: Observable<any>): void{
    resp.subscribe(
      data => {
        this.toastr.success(data.messaggio, "OK", {
          timeOut: 3000, positionClass: "toast-top-center"
        });
        this.ottieniTuttiIMessaggi();
      },
      err => {
        this.toastr.error(err.error.messaggio, "Errore", {
          timeOut: 3000, positionClass: "toast-top-center"
        });
      });
  }

  tronca(contenuto: string): string {
    return contenuto.slice(0, 100) + "...";
  }

  getColore(tipologiaInvito: TipologiaInvito): string {
    switch (tipologiaInvito) {
      case TipologiaInvito.PROPOSTA:
        return "table-primary";
      case TipologiaInvito.RICHIESTA:
        return "table-success";
      case TipologiaInvito.VALUTAZIONE:
        return "table-warning";
      default:
        return "";
    }
  }
}
