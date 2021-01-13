import { Component, OnInit } from '@angular/core';
import { Progetto } from 'src/app/modello/progetto/progetto';
import { ProgettoService } from 'src/app/servizi/progetto.service';

@Component({
  selector: 'app-vetrina-progetti',
  templateUrl: './vetrina-progetti.component.html',
  styleUrls: ['./vetrina-progetti.component.css']
})
export class VetrinaProgettiComponent implements OnInit {

  nomeProgetto: string = "";
  progetti: Progetto[] = [];

  constructor(private progettoService: ProgettoService) { }

  ngOnInit(): void {
    this.vetrina();
  }

  ricerca(): void{
    this.progettoService.cercaProgetto(this.nomeProgetto).subscribe(
      data => {this.progetti = data},
      err => {console.log(err);}
    );
  }

  vetrina(): void{
    this.progettoService.vetrinaProgetti().subscribe(
      data => {this.progetti = data},
      err => {console.log(err);}
    );
  }

  ripristino(): void{
    if(this.nomeProgetto.length == 0) this.vetrina();
  }

}
