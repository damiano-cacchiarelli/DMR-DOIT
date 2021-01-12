import { Component, Input, OnInit } from '@angular/core';
import { EnteDto } from 'src/app/modello/iscritto/ente-dto';
import { IscrittoDto } from 'src/app/modello/iscritto/iscritto-dto';

@Component({
  selector: 'profilo-ente',
  templateUrl: './profilo-ente.component.html',
  styleUrls: ['./profilo-ente.component.css']
})
export class ProfiloEnteComponent implements OnInit {
  @Input() ente?: EnteDto;

  constructor(){
  }
  ngOnInit(): void {
  }

}
