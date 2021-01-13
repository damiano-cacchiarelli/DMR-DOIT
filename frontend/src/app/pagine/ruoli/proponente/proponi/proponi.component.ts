import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Observable } from 'rxjs';
import { InvitoDto } from 'src/app/modello/invito/invito-dto';
import { TipologiaInvito } from 'src/app/modello/invito/tipologia-invito.enum';
import { TipologiaRuolo } from 'src/app/modello/iscritto/tipologia-ruolo.enum';
import { Progetto } from 'src/app/modello/progetto/progetto';
import { ProgettoDto } from 'src/app/modello/progetto/progetto-dto';
import { Tag } from 'src/app/modello/progetto/tag';
import { TagListDto } from 'src/app/modello/progetto/tag-list-dto';
import { SceltaTagComponent } from 'src/app/pagine/progetto/vetrina-progetti/scelta-tag/scelta-tag.component';
import { InvitoService } from 'src/app/servizi/invito.service';
import { ProponenteService } from 'src/app/servizi/proponente.service';
import { TagService } from 'src/app/servizi/tag.service';
import { VisitatoreService } from 'src/app/servizi/visitatore.service';
import { InvitaProgettistiComponent } from '../../proponi/invita-progettisti/invita-progettisti.component';
import { PermettiValutazioneComponent } from '../../proponi/permetti-valutazione/permetti-valutazione.component';

@Component({
  selector: 'app-proponi',
  templateUrl: './proponi.component.html',
  styleUrls: ['./proponi.component.css'],
})
export class ProponiComponent implements OnInit {

  idProgetto?: number;
  @ViewChild("invitaProgettistiComponent") invitaProgettistiComponent?: InvitaProgettistiComponent;
  @ViewChild("permettiValutazioneComponent") permettiValutazioneComponent?: PermettiValutazioneComponent;
  @ViewChild("sceltaTagComponent") sceltaTagComponent?: SceltaTagComponent;
 
  nome: string = null as any;
  obiettivi: string = null as any;
  requisiti: string = null as any;
  //tags: Map<Tag, boolean> = new Map();

  /*
  progettistaEsistente: boolean = true;
  ricercaProgettista: boolean = false;
  idProgettista: string = null as any;
  progettistiInvitati: Set<string> = new Set();
  messaggioProgettisti: string = "";

  espertoEsistente: boolean = true;
  ricercaEsperto: boolean = false;
  idEsperto: string = null as any;
  messaggioEsperto: string = "";
  */

  formProgetto: FormGroup = null as any;

  constructor(
    private proponenteService: ProponenteService,
    private tagService: TagService,
    private visitatoreService: VisitatoreService,
    private invitoService: InvitoService,
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
    );
    /*
    this.tags = new Map([
      [new Tag("Tag1"), false],
      [new Tag("Tag1"), false],
      [new Tag("Tag1"), false],
      [new Tag("Tag1"), false],
      [new Tag("Tag1"), false],
      [new Tag("Tag1"), false]
    ]);
    this.espertiConsigliati = ["esperto1", "esperto1", "esperto1", "esperto1", "esperto1", "esperto1"];
    */

    this.formProgetto = this.formBuilder.group({
      primoCtrl: ['', Validators.required],
      secondoCtrl: ['', Validators.required],
      terzoCtrl: ['', Validators.required]
    });
  }

  onProponi(): void {
    if(!this.sceltaTagComponent) {
      console.log("errore nel metodo proponi");
      return;
    }
    const progetto: ProgettoDto = new ProgettoDto(this.nome, this.obiettivi, this.requisiti, this.sceltaTagComponent.getTagsScelti().tags);
    this.proponenteService.proponi(progetto).subscribe(
      (data: Progetto) => {
        this.idProgetto = data.id;
        this.invitaProgettistiComponent?.invitaProgettisti();
        this.permettiValutazioneComponent?.permettiValutazione();

        this.toastr.success("Progetto creato!", "OK", {
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

  /*
  private invitaProgettisti(idProgetto: number): Observable<any> {
    return this.invitoService.invia(new InvitoDto(this.messaggioProgettisti, TipologiaInvito.PROPOSTA, Array.from(this.progettistiInvitati), idProgetto));
  }*/

  /*
  private permettiValutazione(idProgetto: number, messaggio: string): void {
    if(!this.idEsperto || this.idEsperto.length == 0) {
      this.toastr.success(messaggio, "OK", {
        timeOut: 3000, positionClass: "toast-top-center"
      });
      this.router.navigate(["/"]);
    }
    this.invitoService.invia(new InvitoDto(this.messaggioEsperto, TipologiaInvito.VALUTAZIONE, [this.idEsperto], idProgetto)).subscribe(
      data => { 
        this.toastr.success(messaggio, "OK", {
          timeOut: 3000, positionClass: "toast-top-center"
        });
        this.router.navigate(["/"]);
      },
      err => {
        this.stampaMessaggioErrore(err.error.messaggio);
      }
    );
  }

  private stampaMessaggioErrore(messaggio: string): void {
    this.toastr.error(messaggio, "Errore", {
      timeOut: 3000, positionClass: "toast-top-center"
    });
  }
  */

  /*
  addTag(tag: Tag): void {
    this.tags.set(tag, !this.tags.get(tag));
    if(this.permettiValutazioneComponent){
      this.permettiValutazioneComponent.tags = this.getTagsScelti();
      this.permettiValutazioneComponent.aggiornaEsperti();
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
  }

  /*
  ricercaIdEsperto(): void {
    if (!this.idEsperto || this.idEsperto.length == 0) return;
    this.ricercaEsperto = true;
    console.log("ricerca id esperto " + this.idEsperto);
    this.visitatoreService.getIscrittoByRuolo(this.idEsperto, TipologiaRuolo.ROLE_ESPERTO).subscribe(
      data => {
        if (data.messaggio) {
          this.espertoEsistente = false;
          this.idEsperto = null as any;
        }
        else this.espertoEsistente = true;
        this.ricercaEsperto = false;
      },
      err => {
        this.espertoEsistente = false;
        this.ricercaEsperto = false;
      }
    );
  }

  /*
  ricercaIdProgettista(): void {
    if (!this.idProgettista || this.idProgettista.length == 0) return;
    this.ricercaProgettista = true;
    console.log("ricerca id progettista " + this.idProgettista);
    this.visitatoreService.getIscrittoByRuolo(this.idProgettista, TipologiaRuolo.ROLE_PROGETTISTA).subscribe(
      data => {
        if (data.messaggio) {
          this.progettistaEsistente = false;
          this.idProgettista = null as any;
        }
        else {
          this.progettistiInvitati.add(this.idProgettista);
          this.progettistaEsistente = true;
        }
        this.ricercaProgettista = false;
      },
      err => {
        this.progettistaEsistente = false;
        this.ricercaProgettista = false;
      }
    );
  }

  rimuoviProgettista(progettistaId: string): void {
    this.progettistiInvitati.delete(progettistaId);
  }
  */
}
