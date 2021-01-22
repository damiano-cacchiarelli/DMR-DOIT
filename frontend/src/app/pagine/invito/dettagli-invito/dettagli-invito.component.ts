import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Observable } from 'rxjs';
import { Invito } from 'src/app/modello/invito/invito';
import { RispostaInvitoDto } from 'src/app/modello/invito/risposta-invito-dto';
import { RuoloSoggetto } from 'src/app/modello/invito/ruolo-soggetto.enum';
import { TipologiaInvito } from 'src/app/modello/invito/tipologia-invito.enum';
import { TipologiaRisposta } from 'src/app/modello/invito/tipologia-risposta.enum';
import { TipologiaRuolo } from 'src/app/modello/iscritto/tipologia-ruolo.enum';
import { EspertoService } from 'src/app/servizi/esperto.service';
import { InvitoService } from 'src/app/servizi/invito.service';
import { ProgettistaService } from 'src/app/servizi/progettista.service';
import { ProponenteService } from 'src/app/servizi/proponente.service';
import { TokenService } from 'src/app/servizi/token.service';

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
    private progettistaService: ProgettistaService,
    private tokenService: TokenService,
    private proponenteService: ProponenteService,
    private espertoService:EspertoService,
    private toastr: ToastrService,
    private router: Router
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
    this.gestisci(new RispostaInvitoDto(id, TipologiaRisposta.ACCETTATA));
  }

  onRifiuta(id: string): void {
    this.gestisci(new RispostaInvitoDto(id, TipologiaRisposta.RIFIUTATA));
  }

  private gestisci(rispostaInvito: RispostaInvitoDto): void {
    switch (this.invito?.tipologiaInvito) {
      case TipologiaInvito.PROPOSTA:
        this.responseSub(this.progettistaService.gestisciPropostaPartecipazione(rispostaInvito));
        break;
      case TipologiaInvito.VALUTAZIONE:
        this.responseSub(this.espertoService.rifiutaValutazione(rispostaInvito));
        break;
      case TipologiaInvito.RICHIESTA:
        this.responseSub(this.proponenteService.selezionaCandidati(rispostaInvito));
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
        this.router.navigate(["/bacheca"]);
      },
      err => {
        this.toastr.error(err.error.messaggio, "Errore", {
          timeOut: 3000, positionClass: "toast-top-center"
        });
      });
  }

  hasRuolo(ruolo: string): boolean{
    return this.tokenService.getRuoli().includes(ruolo);
  }
}
