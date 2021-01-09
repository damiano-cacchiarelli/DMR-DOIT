import { Component, OnInit } from '@angular/core';
import { Progetto } from 'src/app/modello/progetto/progetto';

@Component({
  selector: 'anteprima',
  templateUrl: './anteprima.component.html',
  styleUrls: ['./anteprima.component.css']
})
export class AnteprimaComponent implements OnInit {

  progetto?: Progetto;
  constructor(progetto: Progetto) {
    this.progetto = progetto;
   }

  ngOnInit(): void {
  }

}