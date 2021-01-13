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

    /*
    this.tagService.tuttiITag().subscribe(
      data => {
        this.tags = new Map();
        data.forEach((tag: Tag) => {
          this.tags.set(tag, false);
        });
      },
      err => {
        this.toastr.error(err.error.messaggio, "Errore", {
          timeOut: 3000, positionClass: "toast-top-center"
        });
      }
    );*/
  }

  ricerca(): void{
    if(this.nomeProgetto.length==0 || !this.sceltaTagComponent) return;
    this.progettoService.cercaProgetto(this.nomeProgetto, this.sceltaTagComponent.getTagsScelti()).subscribe(
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

  /*
  addTag(tag: Tag): void {
    this.tags.set(tag, !this.tags.get(tag));

    let tagsScelti = this.getTagsScelti();
    if (tagsScelti.tags.length != 0) {
      
    }
  }

  hasTags(): boolean {
    return this.tags.size > 0;
  }

  getTagsScelti(): TagListDto {
    let tagsScelti: Tag[] = [];
    this.tags.forEach((value: boolean, key: Tag) => {
      if (value) tagsScelti.push(key);
    });
    return new TagListDto(tagsScelti);
  }*/
}
