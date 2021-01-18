import { Component, Input, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { Observable } from 'rxjs';
import { InvitoDto } from 'src/app/modello/invito/invito-dto';
import { TipologiaInvito } from 'src/app/modello/invito/tipologia-invito.enum';
import { TipologiaRuolo } from 'src/app/modello/iscritto/tipologia-ruolo.enum';
import { InvitoService } from 'src/app/servizi/invito.service';
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
    private invitoService: InvitoService,
    private proponenteService: ProponenteService,
    private toastr: ToastrService) { }

  ngOnInit(): void {
  }

  invitaProgettisti(): Observable<any> {
    if (this.idProgetto && this.progettistiInvitati.size > 0)
      return this.proponenteService.invitaProgettista(new InvitoDto(this.messaggioProgettisti, TipologiaInvito.PROPOSTA, Array.from(this.progettistiInvitati), this.idProgetto));
    return null as any;
  }

  ricercaIdProgettista(): void {
    if (!this.idProgettista || this.idProgettista.length == 0) return;
    this.ricercaProgettista = true;
    this.visitatoreService.getIscrittoByRuolo(this.idProgettista, TipologiaRuolo.ROLE_PROGETTISTA).subscribe(
      data => {
        console.log(data);
        if (data.messaggio) {
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
