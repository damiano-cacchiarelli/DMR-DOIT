import { Component, Input, OnInit } from '@angular/core';
import { Ente } from 'src/app/modello/iscritto/ente';

@Component({
  selector: 'profilo-ente',
  templateUrl: './profilo-ente.component.html',
  styleUrls: ['./profilo-ente.component.css']
})
export class ProfiloEnteComponent implements OnInit {
  @Input() ente?: Ente;

  constructor(){
  }
  ngOnInit(): void {
  }

}
