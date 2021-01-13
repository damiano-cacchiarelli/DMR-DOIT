import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { IscrittoDto } from 'src/app/modello/iscritto/iscritto-dto';
import { PersonaDto } from 'src/app/modello/iscritto/persona-dto';
import { Fase } from 'src/app/modello/progetto/fase.enum';
import { Progetto } from 'src/app/modello/progetto/progetto';
import { Stato } from 'src/app/modello/progetto/stato.enum';
import { Valutazione } from 'src/app/modello/progetto/valutazione';
import { ValutazioneCandidati } from 'src/app/modello/progetto/valutazione-candidati';
import { ValutazioneDto } from 'src/app/modello/progetto/valutazione-dto';
import { DettagliComponent } from 'src/app/pagine/progetto/dettagli/dettagli.component';
import { EspertoService } from 'src/app/servizi/esperto.service';
import { ProgettoService } from 'src/app/servizi/progetto.service';

@Component({
  selector: 'app-valuta-progetto',
  templateUrl: './valuta-progetto.component.html',
  styleUrls: ['./valuta-progetto.component.css']
})
export class ValutaProgettoComponent implements OnInit {

  progetto: Progetto = null as any;
  recensioneProgetto: string = "";

  progettisti: string[] = [];
  recensioneProgettista: string[] = [];
  indexProgettista: number = 0;

  formValutaProgetto: FormGroup = null as any;

  constructor(
    private progettoService: ProgettoService,
    private espertoService: EspertoService,
    private activatedRoute: ActivatedRoute,
    private toastr: ToastrService,
    private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    const id: number = this.activatedRoute.snapshot.params.id;
    this.progettoService.getProgetto(id).subscribe(
      data => {
        this.progetto = data;
        this.progettisti = this.progetto.gestoreCandidati.identificativiCandidati;
        this.progetto.gestoreCandidati.identificativiPartecipanti.forEach(p => this.progettisti.push(p));
      },
      err => {
        this.toastr.error(err.error.messaggio, "Errore", {
          timeOut: 3000, positionClass: "toast-bottom-right"
        });
      }
    );

    this.formValutaProgetto = this.formBuilder.group({
      primoCtrl: ['', Validators.required]
    });
  }

  onProssimoProgettista(): void {
    if (this.indexProgettista >= this.progettisti.length - 1)
      this.indexProgettista = 0;
    else this.indexProgettista++;
  }

  onPrecedenteProgettista(): void {
    if (this.indexProgettista <= 0)
      this.indexProgettista = this.progettisti.length;
    else this.indexProgettista--;
  }

  onInviaValutazione(): void {
    let valutazioneCandidati: ValutazioneCandidati[] = [];
    for (let index = 0; index < this.progettisti.length; index++) {
      if (this.recensioneProgetto[index] && this.recensioneProgetto[index].length != 0) {
        valutazioneCandidati.push(new ValutazioneCandidati(this.recensioneProgetto[index], this.progettisti[index]));
      }
    }
    const valutazione: ValutazioneDto = new ValutazioneDto(this.recensioneProgetto, this.progetto.id, valutazioneCandidati);
    this.espertoService.valutaProgetto(this.progetto.id, valutazione).subscribe(
      data => {
        this.toastr.error(data.messaggio, "OK", {
          timeOut: 3000, positionClass: "toast-bottom-right"
        });
      },
      err => {
        this.toastr.error(err.error.messaggio, "Errore", {
          timeOut: 3000, positionClass: "toast-bottom-right"
        });
      }
    );
  }
}
