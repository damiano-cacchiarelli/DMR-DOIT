import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Invito } from 'src/app/modello/invito/invito';
import { RispostaInvitoDto } from 'src/app/modello-dto/invito-dto/risposta-invito-dto';
import { RuoloSoggetto } from 'src/app/modello/invito/ruolo-soggetto.enum';
import { TipologiaInvito } from 'src/app/modello/invito/tipologia-invito.enum';
import { TipologiaRisposta } from 'src/app/modello/invito/tipologia-risposta.enum';
import { TipologiaRuolo } from 'src/app/modello/iscritto/ruolo/tipologia-ruolo.enum';
import { InvitoService } from 'src/app/servizi/invito.service';
import { TokenService } from 'src/app/servizi/token.service';
import { UtilsInvito } from '../utils-invito';

@Component({
  selector: 'app-dettagli-invito',
  templateUrl: './dettagli-invito.component.html',
  styleUrls: ['./dettagli-invito.component.css']
})
export class DettagliInvitoComponent implements OnInit {

  invito?: Invito;
  TipologiaRisposta = TipologiaRisposta;
  TipologiaInvito = TipologiaInvito;
  TipologiaRuolo = TipologiaRuolo;
  RuoloSoggetto = RuoloSoggetto;

  constructor(

    private activatedRoute: ActivatedRoute,
    private invitoService: InvitoService,
    private toastr: ToastrService,
    private utilsInvito: UtilsInvito,
    private tokenService: TokenService
  ) { }

  ngOnInit(): void {
    const id = this.activatedRoute.snapshot.params.id;
    this.invitoService.getInvito(id).subscribe(
      data => this.invito = data,
      err => {
        this.toastr.error(err.error.messaggio, "Errore", {
          timeOut: 3000, positionClass: "toast-bottom-right"
        });
      }
    );
  }

  onElimina(id: string): void {
    console.log("NON IMPLEMENATTO: Elimina invito ", id);
  }

  onAccetta(id: string): void {
    if (this.invito) this.utilsInvito.gestisci(new RispostaInvitoDto(id, TipologiaRisposta.ACCETTATA), [this.invito]);
  }

  onRifiuta(id: string): void {
    if (this.invito) this.utilsInvito.gestisci(new RispostaInvitoDto(id, TipologiaRisposta.RIFIUTATA), [this.invito]);
  }

  hasRuolo(ruolo: string): boolean {
    return this.tokenService.hasRuolo(ruolo);
  }
}
