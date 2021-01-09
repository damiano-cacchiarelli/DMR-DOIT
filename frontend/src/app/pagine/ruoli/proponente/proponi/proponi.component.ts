import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ProgettoDto } from 'src/app/modello/progetto/progettoDto';
import { Tag } from 'src/app/modello/progetto/tag';
import { ProponenteService } from 'src/app/servizi/proponente.service';
import { TagService } from 'src/app/servizi/tag.service';

@Component({
  selector: 'app-proponi',
  templateUrl: './proponi.component.html',
  styleUrls: ['./proponi.component.css'],
})
export class ProponiComponent implements OnInit {

  nome: string = null as any;
  obiettivi: string = null as any;
  requisiti: string = null as any;
  tags: Map<Tag, boolean> = new Map();

  constructor(
    private proponenteService: ProponenteService,
    private tagService: TagService,
    private toastr: ToastrService,
    private router: Router) { }

  ngOnInit(): void {
    this.tagService.tuttiITag().subscribe(
      data => {
        this.tags = new Map();
        data.forEach((tag: Tag) => {
          this.tags.set(tag, false);
        });

        this.tags = new Map([
          [new Tag("Tag1"), false],
          [new Tag("Tag1"), false],
          [new Tag("Tag1"), false],
          [new Tag("Tag1"), false],
          [new Tag("Tag1"), false],
          [new Tag("Tag1"), false]
        ]);
      },
      err => {
        this.toastr.error(err.error.messaggio, "Errore", {
          timeOut: 3000, positionClass: "toast-top-center"
        });
      }
    );
  }

  onProponi(): void {
    let tagsScelti: Tag[] = [];
    this.tags.forEach((value: boolean, key: Tag) => {
      if (value) tagsScelti.push(key);
    });
    const progetto: ProgettoDto = new ProgettoDto(this.nome, this.obiettivi, this.requisiti, tagsScelti);
    this.proponenteService.proponi(progetto).subscribe(
      data => {
        this.toastr.success(data.messaggio, "OK", {
          timeOut: 3000, positionClass: "toast-top-center"
        });
        this.router.navigate(["/"]);
      },
      err => {
        this.toastr.error(err.error.messaggio, "Errore", {
          timeOut: 3000, positionClass: "toast-top-center"
        });
      }
    );
  }

  addTag(tag: Tag): void {
    this.tags.set(tag, !this.tags.get(tag));
  }

  hasTags(): boolean{
    return this.tags.size > 0;
  }


}
