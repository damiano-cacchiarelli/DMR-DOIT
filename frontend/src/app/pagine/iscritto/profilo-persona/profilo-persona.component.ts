import { Component, Input, OnInit } from '@angular/core';
import { Persona } from 'src/app/modello/iscritto/persona';

@Component({
  selector: 'profilo-persona',
  templateUrl: './profilo-persona.component.html',
  styleUrls: ['./profilo-persona.component.css']
})
export class ProfiloPersonaComponent implements OnInit {
  @Input() persona?: Persona;

  constructor() {
  }

  ngOnInit(): void {
  }

}
