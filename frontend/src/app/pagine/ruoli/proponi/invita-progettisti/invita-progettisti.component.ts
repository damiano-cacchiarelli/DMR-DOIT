import { Component, Input, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { InvitoDto } from 'src/app/modello-dto/invito-dto/invito-dto';
import { TipologiaInvito } from 'src/app/modello/invito/tipologia-invito.enum';
import { TipologiaRuolo } from 'src/app/modello/iscritto/ruolo/tipologia-ruolo.enum';
import { ProponenteService } from 'src/app/servizi/proponente.service';
import { VisitatoreService } from 'src/app/servizi/visitatore.service';

@Component({
  selector: 'invita-progettisti',
  templateUrl: './invita-progettisti.component.html',
  styleUrls: ['./invita-progettisti.component.css']
})
export class InvitaProgettistiComponent implements OnInit {

  @Input() idProgetto?: number;

  progettistaEsistente: boolean = true;
  ricercaProgettista: boolean = false;
  idProgettista: string = null as any;
  progettistiInvitati: Set<string> = new Set();
  messaggioProgettisti: string = "";

  constructor(
    private visitatoreService: VisitatoreService,
    private proponenteService: ProponenteService) { }

  ngOnInit(): void {
  }

  public invitaProgettisti(): Observable<any> {
    if (this.idProgetto && this.progettistiInvitati.size > 0) {
      const resp = this.proponenteService.invitaProgettista(new InvitoDto(this.messaggioProgettisti, TipologiaInvito.PROPOSTA, Array.from(this.progettistiInvitati), this.idProgetto));
      this.reset();
      return resp;
    }
    this.reset();
    return null as any;
  }

  reset(): void {
    this.progettistaEsistente = true;
    this.ricercaProgettista = false;
    this.idProgettista = null as any;
    this.progettistiInvitati = new Set();
    this.messaggioProgettisti = "";
  }

  ricercaIdProgettista(): void {
    if (!this.idProgettista || this.idProgettista.length == 0) return;
    this.ricercaProgettista = true;
    this.visitatoreService.getIscrittoByRuolo(this.idProgettista, TipologiaRuolo.ROLE_PROGETTISTA).subscribe(
      data => {
        if (!data) {
          this.progettistaEsistente = false;
          this.idProgettista = null as any;
        }
        else {
          this.progettistiInvitati.add(this.idProgettista);
          this.progettistaEsistente = true;
        }
        this.ricercaProgettista = false;
      },
      err => {
        this.progettistaEsistente = false;
        this.ricercaProgettista = false;
      }
    );
  }

  rimuoviProgettista(progettistaId: string): void {
    this.progettistiInvitati.delete(progettistaId);
  }
}
