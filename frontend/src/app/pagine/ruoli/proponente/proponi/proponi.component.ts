import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ProgettoDto } from 'src/app/modello/progetto/progettoDto';
import { Tag } from 'src/app/modello/progetto/tag';
import { IscrittoService } from 'src/app/servizi/iscritto.service';
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

  progettistaEsistente: boolean = true;
  ricercaProgettista: boolean = false;
  idProgettista: string = null as any;
  progettistiInvitati: Set<string> = new Set();
  messaggioProgettisti: string = null as any;

  espertoEsistente: boolean = true;
  ricercaEsperto: boolean = false;
  idEsperto: string = null as any;
  messaggioEsperto: string = null as any;

  formProgetto: FormGroup = null as any;

  constructor(
    private proponenteService: ProponenteService,
    private tagService: TagService,
    private iscrittoService: IscrittoService,
    private toastr: ToastrService,
    private router: Router,
    private formBuilder: FormBuilder) { }

  ngOnInit(): void {
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
    this.tags = new Map([
      [new Tag("Tag1"), false],
      [new Tag("Tag1"), false],
      [new Tag("Tag1"), false],
      [new Tag("Tag1"), false],
      [new Tag("Tag1"), false],
      [new Tag("Tag1"), false]
    ]);
    this.formProgetto = this.formBuilder.group({
      primoCtrl: ['', Validators.required],
      secondoCtrl: ['', Validators.required],
      terzoCtrl: ['', Validators.required]
    });
  }

  onProponi(): void {
    const progetto: ProgettoDto = new ProgettoDto(this.nome, this.obiettivi, this.requisiti, this.getTagsScelti());
    this.proponenteService.proponi(progetto).subscribe(
      data => {
        /* 
          invita progettisti
          richiedi valutazione
        */
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

  hasTags(): boolean {
    return this.tags.size > 0;
  }

  getTagsScelti(): Tag[] {
    let tagsScelti: Tag[] = [];
    this.tags.forEach((value: boolean, key: Tag) => {
      if (value) tagsScelti.push(key);
    });
    return tagsScelti;
  }

  ricercaIdEsperto(): void {
    if (!this.idEsperto || this.idEsperto.length == 0) return;
    this.ricercaEsperto = true;
    console.log("ricerca id esperto " + this.idEsperto);
    // richiesta al server per l'id. Se esiste
    this.ricercaEsperto = false;
  }

  ricercaIdProgettista(): void {
    if (!this.idProgettista || this.idProgettista.length == 0) return;
    this.ricercaProgettista = true;
    console.log("ricerca id progettista " + this.idProgettista);
    // richiesta al server per l'id. Se esiste
    this.progettistiInvitati.add(this.idProgettista);
    this.ricercaProgettista = false;
  }

  rimuoviProgettista(progettistaId: string): void {
    this.progettistiInvitati.delete(progettistaId);
  }
}
