import { Component, Input, OnInit } from '@angular/core';
import { resetFakeAsyncZone } from '@angular/core/testing';
import { ToastrService } from 'ngx-toastr';
import { Observable } from 'rxjs';
import { InvitoDto } from 'src/app/modello/invito/invito-dto';
import { TipologiaInvito } from 'src/app/modello/invito/tipologia-invito.enum';
import { TipologiaRuolo } from 'src/app/modello/iscritto/tipologia-ruolo.enum';
import { TagListDto } from 'src/app/modello/progetto/tag-list-dto';
import { InvitoService } from 'src/app/servizi/invito.service';
import { ProponenteService } from 'src/app/servizi/proponente.service';
import { VisitatoreService } from 'src/app/servizi/visitatore.service';

@Component({
  selector: 'permetti-valutazione',
  templateUrl: './permetti-valutazione.component.html',
  styleUrls: ['./permetti-valutazione.component.css']
})
export class PermettiValutazioneComponent implements OnInit {

  @Input() idProgetto?: number;
  @Input() tags: TagListDto = new TagListDto([]);

  espertoEsistente: boolean = true;
  ricercaEsperto: boolean = false;
  idEsperto: string = null as any;
  messaggioEsperto: string = "";
  espertiConsigliati: string[] = [];

  constructor(
    private proponenteService: ProponenteService,
    private visitatoreService: VisitatoreService,
    private invitoService: InvitoService,
    private toastr: ToastrService
  ) { }

  ngOnInit(): void {
    this.aggiornaEsperti();
  }

  ricercaIdEsperto(): void {
    if (!this.idEsperto || this.idEsperto.length == 0) return;
    this.ricercaEsperto = true;
    console.log("ricerca id esperto " + this.idEsperto);
    this.visitatoreService.getIscrittoByRuolo(this.idEsperto, TipologiaRuolo.ROLE_ESPERTO).subscribe(
      data => {
        if (!data) {
          this.espertoEsistente = false;
          this.idEsperto = null as any;
        }
        else this.espertoEsistente = true;
        this.ricercaEsperto = false;
      },
      err => {
        this.espertoEsistente = false;
        this.ricercaEsperto = false;
      }
    );
  }

  permettiValutazione(): void {
    if (!this.idEsperto || this.idEsperto.length == 0 || !this.idProgetto) return;
    //this.invitoService.invia(new InvitoDto(this.messaggioEsperto, TipologiaInvito.VALUTAZIONE, [this.idEsperto], this.idProgetto)).subscribe(
    this.proponenteService.permettiValutazione(new InvitoDto(this.messaggioEsperto, TipologiaInvito.VALUTAZIONE, [this.idEsperto], this.idProgetto)).subscribe(
      data => {
        this.toastr.success(data.messaggio, "OK", {
          timeOut: 3000, positionClass: "toast-top-center"
        });
      },
      err => {
        this.toastr.error(err.error.messaggio, "Errore", {
          timeOut: 3000, positionClass: "toast-top-center"
        });
      }
    );
    console.log("permetti valutazione");
    this.reset();
  }

  permettiValutazioneObs(): Observable<any> {
    if (!this.idEsperto || this.idEsperto.length == 0 || !this.idProgetto) return null as any;
    
    const res = this.proponenteService.permettiValutazione(new InvitoDto(this.messaggioEsperto, TipologiaInvito.VALUTAZIONE, [this.idEsperto], this.idProgetto));
    this.reset();
    return res;
  }

  aggiornaEsperti() {
    this.proponenteService.espertiConsigliati(this.tags).subscribe(
      data => {
        data.forEach(e => {
          this.espertiConsigliati.push(e.identificativo);
        });
      },
      err => {
        console.log(err);
      }
    );
  }

  reset(): void {
    this.espertoEsistente = true;
    this.ricercaEsperto = false;
    this.idEsperto = null as any;
    this.messaggioEsperto = "";
    this.espertiConsigliati = [];
  }

}
