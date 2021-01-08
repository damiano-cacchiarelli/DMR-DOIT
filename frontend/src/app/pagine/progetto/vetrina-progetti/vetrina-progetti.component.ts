import { Component, OnInit } from '@angular/core';
import { ProgettoDettagliato } from 'src/app/modello/progetto/progetto-dettagliato';
import { ProgettoService } from 'src/app/servizi/progetto.service';

@Component({
  selector: 'app-vetrina-progetti',
  templateUrl: './vetrina-progetti.component.html',
  styleUrls: ['./vetrina-progetti.component.css']
})
export class VetrinaProgettiComponent implements OnInit {

  progetti: ProgettoDettagliato[] = [];

  constructor(private progettoService: ProgettoService) { }

  ngOnInit(): void {
    this.progettoService.vetrinaProgetti().subscribe(
      data => {this.progetti = data},
      err => {console.log(err);}
    );
  }

}
