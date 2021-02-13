import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Progetto } from 'src/app/modello/progetto/progetto';
import { ValutazioneCandidati } from 'src/app/modello/progetto/valutazione-candidati';
import { ValutazioneDto } from 'src/app/modello-dto/progetto-dto/valutazione-dto';
import { ProfiloComponent } from 'src/app/pagine/iscritto/profilo/profilo.component';
import { EspertoService } from 'src/app/servizi/esperto.service';
import { ProgettoService } from 'src/app/servizi/progetto.service';

@Component({
  selector: 'app-valuta-progetto',
  templateUrl: './valuta-progetto.component.html',
  styleUrls: ['./valuta-progetto.component.css']
})
export class ValutaProgettoComponent implements OnInit {

  @ViewChild("profiloComponent") profiloComponent?: ProfiloComponent;

  idInvito: string = "";
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
    private formBuilder: FormBuilder,
    private router: Router) { }

  ngOnInit(): void {
    this.formValutaProgetto = this.formBuilder.group({
      primoCtrl: ['', Validators.required]
    });

    const id: number = this.activatedRoute.snapshot.params.id;
    this.idInvito = this.activatedRoute.snapshot.params.idInvito;
    this.progettoService.getProgetto(id).subscribe(
      data => {
        this.progetto = data;
        this.progetto.gestoreCandidati.identificativiCandidati.forEach(p => this.progettisti.push(p));
        this.progetto.gestoreCandidati.identificativiPartecipanti.forEach(p => this.progettisti.push(p));
      },
      err => {
        this.toastr.error(err.error.messaggio, "Errore", {
          timeOut: 3000, positionClass: "toast-bottom-right"
        });
      }
    );
  }

  onProssimoProgettista(): void {
    if (this.indexProgettista >= this.progettisti.length - 1)
      this.indexProgettista = 0;
    else this.indexProgettista++;

    if (this.profiloComponent){
      this.profiloComponent.identificativo = this.progettisti[this.indexProgettista];
      this.profiloComponent.updateProfilo();
    }
  }

  onPrecedenteProgettista(): void {
    if (this.indexProgettista <= 0)
      this.indexProgettista = this.progettisti.length - 1;
    else this.indexProgettista--;

    if (this.profiloComponent){
      this.profiloComponent.identificativo = this.progettisti[this.indexProgettista];
      this.profiloComponent.updateProfilo();
    }
  }

  onInviaValutazione(): void {
    let valutazioneCandidati: ValutazioneCandidati[] = [];
    for (let index = 0; index < this.progettisti.length; index++) {
      if (this.recensioneProgettista[index] && this.recensioneProgettista[index].length != 0) {
        valutazioneCandidati.push(new ValutazioneCandidati(this.recensioneProgettista[index], this.progettisti[index]));
      }
    }   
    const valutazione: ValutazioneDto = new ValutazioneDto(this.recensioneProgetto, this.progetto.id, valutazioneCandidati);
    this.espertoService.valutaProgetto(this.idInvito, valutazione).subscribe(
      data => {
        this.toastr.success(data.messaggio, "OK", {
          timeOut: 3000, positionClass: "toast-bottom-right"
        });

        this.router.navigate([".."]);
      },
      err => {
        console.log(err);
        this.toastr.error(err.error.messaggio, "Errore", {
          timeOut: 3000, positionClass: "toast-bottom-right"
        });
      }
    );
  }
}
