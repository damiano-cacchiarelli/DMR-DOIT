import { Component, OnInit, ViewChild } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { Progetto } from 'src/app/modello/progetto/progetto';
import { Tag } from 'src/app/modello/progetto/tag';
import { TagListDto } from 'src/app/modello/progetto/tag-list-dto';
import { ProgettoService } from 'src/app/servizi/progetto.service';
import { TagService } from 'src/app/servizi/tag.service';
import { SceltaTagComponent } from './scelta-tag/scelta-tag.component';

@Component({
  selector: 'app-vetrina-progetti',
  templateUrl: './vetrina-progetti.component.html',
  styleUrls: ['./vetrina-progetti.component.css']
})
export class VetrinaProgettiComponent implements OnInit {

  @ViewChild("sceltaTagComponent") sceltaTagComponent?: SceltaTagComponent;
  nomeProgetto: string = "";
  progetti: Progetto[] = [];

  //tags: Map<Tag, boolean> = new Map();

  constructor(
    private progettoService: ProgettoService,
    private tagService: TagService,
    private toastr: ToastrService) { }

  ngOnInit(): void {
    this.vetrina();
  }

  public getColore(i: number): string {
    const colore = Math.floor(Math.random() * Tag.COLORI.length + 1);
    return "bg-" + Tag.COLORI[(i + colore) % Tag.COLORI.length];
  }

  ricerca(): void {
    if (this.nomeProgetto.length == 0 || !this.sceltaTagComponent) return;
    this.progettoService.cercaProgetto(this.nomeProgetto, this.sceltaTagComponent.getTagsScelti()).subscribe(
      data => { this.progetti = data },
      err => { console.log(err); }
    );
  }

  vetrina(): void {
    this.progettoService.vetrinaProgetti().subscribe(
      data => { this.progetti = data },
      err => { console.log(err); }
    );
  }

  ripristino(): void {
    if (this.nomeProgetto.length == 0) this.vetrina();
  }
}
