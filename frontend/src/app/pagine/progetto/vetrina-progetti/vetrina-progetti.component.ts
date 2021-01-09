import { Component, OnInit } from '@angular/core';
import { Progetto } from 'src/app/modello/progetto/progetto';
import { ProgettoService } from 'src/app/servizi/progetto.service';

@Component({
  selector: 'app-vetrina-progetti',
  templateUrl: './vetrina-progetti.component.html',
  styleUrls: ['./vetrina-progetti.component.css']
})
export class VetrinaProgettiComponent implements OnInit {

  progetti: Progetto[] = [];

  constructor(private progettoService: ProgettoService) { }

  ngOnInit(): void {
    this.progettoService.vetrinaProgetti().subscribe(
      data => {this.progetti = data},
      err => {console.log(err);}
    );
  }

}
