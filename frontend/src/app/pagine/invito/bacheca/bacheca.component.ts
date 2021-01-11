import { Component, OnInit } from '@angular/core';
import { Invito } from 'src/app/modello/invito/invito';
import { InvitoDto } from 'src/app/modello/invito/invito-dto';
import { RuoloSoggetto } from 'src/app/modello/invito/ruolo-soggetto.enum';
import { TipologiaInvito } from 'src/app/modello/invito/tipologia-invito.enum';
import { TipologiaRisposta } from 'src/app/modello/invito/tipologia-risposta.enum';
import { Progetto } from 'src/app/modello/progetto/progetto';

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

  constructor() { }

  ngOnInit(): void {
    this.inviti = [
      new Invito("contenuto", TipologiaInvito.PROPOSTA, "mat", 1, "mat1", RuoloSoggetto.DESTINATARIO, new Date(), TipologiaRisposta.IN_ATTESA, "dam", new Progetto("Nome progetto", "", "", [], 1, null as any, null as any, null as any, "", null as any))
,
new Invito("contenuto", TipologiaInvito.VALUTAZIONE, "mat", 1, "mat1", RuoloSoggetto.DESTINATARIO, new Date(), TipologiaRisposta.IN_ATTESA, "dam", new Progetto("Nome progetto", "", "", [], 1, null as any, null as any, null as any, "", null as any))
,
new Invito("contenuto", TipologiaInvito.PROPOSTA, "mat", 1, "mat1", RuoloSoggetto.DESTINATARIO, new Date(), TipologiaRisposta.IN_ATTESA, "dam", new Progetto("Nome progetto", "", "", [], 1, null as any, null as any, null as any, "", null as any))
,
new Invito("contenuto", TipologiaInvito.RICHIESTA, "mat", 1, "mat1", RuoloSoggetto.DESTINATARIO, new Date(), TipologiaRisposta.IN_ATTESA, "dam", new Progetto("Nome progetto", "", "", [], 1, null as any, null as any, null as any, "", null as any))
,
new Invito("contenuto", TipologiaInvito.PROPOSTA, "mat", 1, "mat1", RuoloSoggetto.DESTINATARIO, new Date(), TipologiaRisposta.IN_ATTESA, "dam", new Progetto("Nome progetto", "", "", [], 1, null as any, null as any, null as any, "", null as any))
    ];

    this.tuttiIMessaggi();
  }

  onCercaMessaggio(): void {
    console.log(this.cercaMessaggio);
    if (this.cercaMessaggio.length == 0) {
      this.tuttiIMessaggi();
      return;
    }
    this.messaggi = [];
    this.inviti.forEach(i => {
      if (i.contenuto.includes(this.cercaMessaggio))
        this.messaggi.push(i);
    });
  }

  tuttiIMessaggi(): void {
    this.messaggi = [];
    this.messaggi = this.inviti;
  }

  messaggiInviati(): void {
    this.messaggi = [];
    let bo = true;
    this.inviti.forEach(i => {
      if (bo) {
        bo = false;
        this.messaggi.push(i);
      } else bo = true;
    });
  }

  messaggiRicevuti(): void {
    this.messaggi = [];
    let bo = false;
    this.inviti.forEach(i => {
      if (bo) {
        bo = false;
        this.messaggi.push(i);
      } else bo = true;
    });
  }

  onElimina(id: string): void {
    console.log("elimina invito ", id);
  }

  onAccetta(id: string): void {
    console.log("accetta invito ", id);
  }

  onRifiuta(id: string): void {
    console.log("rifiuta invito ", id);
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
