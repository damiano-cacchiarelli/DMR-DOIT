import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { Invito } from 'src/app/modello/invito/invito';
import { RispostaInvitoDto } from 'src/app/modello/invito/risposta-invito-dto';
import { RuoloSoggetto } from 'src/app/modello/invito/ruolo-soggetto.enum';
import { TipologiaInvito } from 'src/app/modello/invito/tipologia-invito.enum';
import { TipologiaRisposta } from 'src/app/modello/invito/tipologia-risposta.enum';
import { InvitoService } from 'src/app/servizi/invito.service';

@Component({
  selector: 'app-bacheca',
  templateUrl: './bacheca.component.html',
  styleUrls: ['./bacheca.component.css']
})
export class BachecaComponent implements OnInit {

  private inviti: Invito[] = []; // InvitoDto da rimpiazzare con Invito
  messaggi: Invito[] = []; // InvitoDto da rimpiazzare con Invito

  TipologiaInvito = TipologiaInvito;
  TipologiaRisposta = TipologiaRisposta;

  cercaMessaggio: string = "";

  constructor(
    private invitoService: InvitoService,
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
    console.log("elimina invito ", id);
  }

  onAccetta(id: string): void {
    this.gestisci(new RispostaInvitoDto(id, TipologiaRisposta.ACCETTATA));
    console.log("accetta invito ", id);
  }

  onRifiuta(id: string): void {
    this.gestisci(new RispostaInvitoDto(id, TipologiaRisposta.RIFIUTATA));
    console.log("rifiuta invito ", id);
  }

  private gestisci(rispostaInvito: RispostaInvitoDto): void {
    this.invitoService.gestisci(rispostaInvito).subscribe(
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
