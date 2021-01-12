import { Component, Input, OnInit } from '@angular/core';
import { IscrittoDto } from 'src/app/modello/iscritto/iscritto-dto';
import { PersonaDto } from 'src/app/modello/iscritto/persona-dto';

@Component({
  selector: 'profilo-persona',
  templateUrl: './profilo-persona.component.html',
  styleUrls: ['./profilo-persona.component.css']
})
export class ProfiloPersonaComponent implements OnInit {
  @Input() persona?:PersonaDto;
  
 constructor(){
 }

  ngOnInit(): void {
  }

}
