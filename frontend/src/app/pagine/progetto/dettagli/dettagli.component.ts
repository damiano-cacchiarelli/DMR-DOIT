import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Progetto } from 'src/app/modello/progetto/progetto';
import { ProgettoService } from 'src/app/servizi/progetto.service';
import { Tag } from 'src/app/modello/progetto/tag';
import { TokenService } from 'src/app/servizi/token.service';
import { TipologiaRuolo } from 'src/app/modello/iscritto/ruolo/tipologia-ruolo.enum';
import { Stato } from 'src/app/modello/progetto/stato.enum';
import { Fase } from 'src/app/modello/progetto/fase.enum';
import { ProponenteService } from 'src/app/servizi/proponente.service';
import { InvitaProgettistiComponent } from '../../ruoli/proponi/invita-progettisti/invita-progettisti.component';
import { PermettiValutazioneComponent } from '../../ruoli/proponi/permetti-valutazione/permetti-valutazione.component';
import { ProgettistaService } from 'src/app/servizi/progettista.service';
import { InvitoDto } from 'src/app/modello-dto/invito-dto/invito-dto';
import { TipologiaInvito } from 'src/app/modello/invito/tipologia-invito.enum';

@Component({
  selector: 'app-dettagli',
  templateUrl: './dettagli.component.html',
  styleUrls: ['./dettagli.component.css']
})
export class DettagliComponent implements OnInit {

  @Input() progetto?: Progetto;
  @Input() valutazione = false;

  @ViewChild("invitaProgettisti") invitaProgettisti?: InvitaProgettistiComponent;
  @ViewChild("permettiValutazioneProgetto") permettiValutazioneProgetto?: PermettiValutazioneComponent;

  private idProgetto: number = 0;
  private colore: number = 0;
  TipologiaRuolo = TipologiaRuolo;
  Stato = Stato;
  Fase = Fase;
  valutazioneCorrente: number = 0;

  opzioniModal = { titolo: "", messaggio: "", selettoreDaAttivare: "", onClickProcedi: () => { } };

  constructor(
    private progettoService: ProgettoService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private toastr: ToastrService,
    private tokenService: TokenService,
    private progettistaService: ProgettistaService,
    private proponenteService: ProponenteService) { }

  ngOnInit(): void {

    this.colore = Math.floor(Math.random() * Tag.COLORI.length + 1);
    this.idProgetto = this.activatedRoute.snapshot.params.id;
    this.aggiornaProgetto();
  }

  private aggiornaProgetto(): void {
    this.progettoService.getProgetto(this.idProgetto).subscribe(
      data => {
        this.progetto = data;
      },
      err => {
        this.toastr.error(err.error.messaggio, "Errore", {
          timeOut: 3000, positionClass: "toast-bottom-right"
        });
      }
    );
  }

  public getColore(i: number): string {
    return "btn-" + Tag.COLORI[(i + this.colore) % Tag.COLORI.length];
  }

  chiudiCandidature(): void {
    this.opzioniModal.titolo = "Chiusura candidature";
    this.opzioniModal.messaggio = "Sei sicuro di voler chiudere le candidature? Attenzione: una volta chiuse non possono essre più aperte!";
    this.opzioniModal.onClickProcedi = () => {
      if (this.progetto)
        this.proponenteService.chiudiCandidature(this.progetto.id).subscribe(
          data => {
            this.toastr.success(data.messaggio, "OK", {
              timeOut: 3000, positionClass: "toast-bottom-right"
            });
            this.home();
          },
          err => {
            this.toastr.error(err.error.messaggio, "Errore", {
              timeOut: 3000, positionClass: "toast-bottom-right"
            });
          }
        );
    }
  }

  faseSuccessiva(): void {
    this.opzioniModal.titolo = "Passa a fase successiva";
    this.opzioniModal.messaggio = "Sei sicuro di voler passare alla fase successiva? Attenzione! Di seguito sono elencate le operazioni che non possono esser fatte nella fase successiva: " + this.progetto?.operazioniFase;
    this.opzioniModal.onClickProcedi = () => {
      if (this.progetto)
        this.proponenteService.faseSuccessiva(this.progetto.id).subscribe(
          data => {
            this.toastr.success(data.messaggio, "OK", {
              timeOut: 3000, positionClass: "toast-bottom-right"
            });
            this.home();
          },
          err => {
            this.toastr.error(err.error.messaggio, "Errore", {
              timeOut: 3000, positionClass: "toast-bottom-right"
            });
          }
        );
    }
  }

  permettiValutazione(): void {
    this.opzioniModal.titolo = "Permetti valutazione progetto";
    this.opzioniModal.selettoreDaAttivare = "permetti-valutazione";
    this.opzioniModal.onClickProcedi = () => {
      if (this.permettiValutazioneProgetto) this.permettiValutazioneProgetto.idProgetto = this.progetto?.id;
      this.permettiValutazioneProgetto?.permettiValutazioneObs().subscribe(
        data => {
          this.toastr.success(data.messaggio, "OK", {
            timeOut: 3000, positionClass: "toast-top-center"
          });
          this.aggiornaProgetto();
        },
        err => {
          this.toastr.error(err.error.messaggio, "Errore", {
            timeOut: 3000, positionClass: "toast-top-center"
          });
        });

    }
  }

  invitaProgettista(): void {
    this.opzioniModal.titolo = "Invita progettisti";
    this.opzioniModal.selettoreDaAttivare = "invita-progettisti";
    this.opzioniModal.onClickProcedi = () => {
      if (this.invitaProgettisti) this.invitaProgettisti.idProgetto = this.progetto?.id;
      this.invitaProgettisti?.invitaProgettisti().subscribe(
        data => {
          this.toastr.success(data.messaggio, "OK", {
            timeOut: 3000, positionClass: "toast-top-center"
          });
          this.aggiornaProgetto();
        },
        err => {
          this.toastr.error(err.error.messaggio, "Errore", {
            timeOut: 3000, positionClass: "toast-top-center"
          });
        });
    }
  }

  partecipa(): void {
    this.opzioniModal.titolo = "Partecipa al progetto";
    this.opzioniModal.messaggio = "Sei sicuro di voler candidarti al progetto?";
    this.opzioniModal.onClickProcedi = () => {
      if (this.progetto)
        this.progettistaService.candidati(new InvitoDto("Vorrei candidarmi al progetto", TipologiaInvito.RICHIESTA, [this.progetto.idProponente], this.progetto.id)).subscribe(
          data => {
            this.toastr.success(data.messaggio, "OK", {
              timeOut: 3000, positionClass: "toast-bottom-right"
            });
            this.home();
          },
          err => {
            this.toastr.error(err.error.messaggio, "Errore", {
              timeOut: 3000, positionClass: "toast-bottom-right"
            });
          }
        );
    }
  }

  chiudiModal(): void {
    this.opzioniModal.titolo = "";
    this.opzioniModal.messaggio = "";
    this.opzioniModal.selettoreDaAttivare = "";
    this.opzioniModal.onClickProcedi = null as any;
  }

  procediModal(): void {
    if (this.opzioniModal.onClickProcedi)
      this.opzioniModal.onClickProcedi();
  }

  hasRuolo(ruolo: TipologiaRuolo): boolean {
    return this.tokenService.getRuoli().includes(ruolo);
  }

  proprietarioProgetto(): boolean {
    return this.progetto?.idProponente == this.tokenService.getIdentificativo();
  }

  vautazioniCandidati(i: number): void {
    this.opzioniModal.titolo = "Valutazioni progettisti";
    this.opzioniModal.selettoreDaAttivare = "valutazioni-candidati";
    this.valutazioneCorrente = i;
  }

  private home(): void {
    this.router.navigate(["/"]);
  }
}
