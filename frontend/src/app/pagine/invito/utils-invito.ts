import { ToastrService } from "ngx-toastr";
import { Observable } from "rxjs";
import { RispostaInvitoDto } from "src/app/modello-dto/invito-dto/risposta-invito-dto";
import { Invito } from "src/app/modello/invito/invito";
import { TipologiaInvito } from "src/app/modello/invito/tipologia-invito.enum";
import { ProgettistaService } from "src/app/servizi/progettista.service";
import { ProponenteService } from "src/app/servizi/proponente.service";
import { EspertoService } from "src/app/servizi/esperto.service";
import { Router } from "@angular/router";
import { Injectable } from "@angular/core";

@Injectable({
    providedIn: 'root'
})
export class UtilsInvito {

    constructor(private progettistaService: ProgettistaService,
        private espertoService: EspertoService,
        private proponenteService: ProponenteService,
        private toastr: ToastrService,
        private router: Router) {

    }

    public gestisci(rispostaInvito: RispostaInvitoDto, inviti: Invito[]): void {
        let i: Invito = null as any;
        inviti.forEach(invi => { if (invi.id == rispostaInvito.idInvito) i = invi; });
        switch (i.tipologiaInvito) {
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

    private responseSub(resp: Observable<any>): void {
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
}
