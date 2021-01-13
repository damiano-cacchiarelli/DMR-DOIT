import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { EnteDto } from 'src/app/modello/iscritto/ente-dto';
import { IscrittoDto } from 'src/app/modello/iscritto/iscritto-dto';
import { PersonaDto } from 'src/app/modello/iscritto/persona-dto';
import { Fase } from 'src/app/modello/progetto/fase.enum';
import { Progetto } from 'src/app/modello/progetto/progetto';
import { Stato } from 'src/app/modello/progetto/stato.enum';

@Component({
  selector: 'app-valuta-progetto',
  templateUrl: './valuta-progetto.component.html',
  styleUrls: ['./valuta-progetto.component.css']
})
export class ValutaProgettoComponent implements OnInit {

  progetto: Progetto = null as any;
  recensioneProgetto: string = "";

  progettisti: IscrittoDto[] = [];
  recensioneProgettista: string[] = [];
  indexProgettista: number = 0;

  formValutaProgetto: FormGroup = null as any;

  constructor(
    private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.progetto = new Progetto("nome", "obiettivi", "requisiti", [], 2, Stato.IN_VALUTAZIONE, Fase.INIZIO, new Date(), "mat", null as any);

    this.progettisti = [
      new PersonaDto("progg1", "email", "pass", "nome1", "cognome", "cittadinanza", "maschio", "072222222"),
      new PersonaDto("progg2", "email", "pass", "nome2", "cognome", "cittadinanza", "maschio", "072222222"),
      new PersonaDto("progg3", "email", "pass", "nome3", "cognome", "cittadinanza", "maschio", "072222222"),
      new PersonaDto("progg4", "email", "pass", "nome4", "cognome", "cittadinanza", "maschio", "072222222"),
      new PersonaDto("progg5", "email", "pass", "nome5", "cognome", "cittadinanza", "maschio", "072222222"),
    ];

    this.formValutaProgetto = this.formBuilder.group({
      primoCtrl: ['', Validators.required]
    });
  }

  onProssimoProgettista(): void {
    if (this.indexProgettista >= this.progettisti.length-1)
      this.indexProgettista = 0;
    else this.indexProgettista++;
  }

  onPrecedenteProgettista(): void {
    if (this.indexProgettista <= 0)
      this.indexProgettista = this.progettisti.length;
    else this.indexProgettista--;
  }

  onInviaValutazione(): void {
    console.log("onInviaValutazione");
  }
}
