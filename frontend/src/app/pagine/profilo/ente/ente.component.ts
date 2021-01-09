import { Component, OnInit } from '@angular/core';
import { Ente } from 'src/app/modello/iscritto/ente';

@Component({
  selector: 'app-ente',
  templateUrl: './ente.component.html',
  styleUrls: ['./ente.component.css']
})
export class EnteComponent implements OnInit {

  ente?: Ente;

  constructor(ente:Ente) {
  this.ente = ente
  }

  ngOnInit(): void {
  }

}
