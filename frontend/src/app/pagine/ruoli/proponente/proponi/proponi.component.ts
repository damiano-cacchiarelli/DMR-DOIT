import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Progetto } from 'src/app/modello/progetto/progetto';
import { ProgettoDto } from 'src/app/modello-dto/progetto-dto/progetto-dto';
import { SceltaTagComponent } from 'src/app/pagine/progetto/vetrina-progetti/scelta-tag/scelta-tag.component';
import { ProponenteService } from 'src/app/servizi/proponente.service';
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

  formProgetto: FormGroup = null as any;

  constructor(
    private proponenteService: ProponenteService,
    private toastr: ToastrService,
    private router: Router,
    private formBuilder: FormBuilder) { }

  ngOnInit(): void {

    if (this.permettiValutazioneComponent && this.sceltaTagComponent)
      this.permettiValutazioneComponent.tags = this.sceltaTagComponent.getTagsScelti();
    this.formProgetto = this.formBuilder.group({
      primoCtrl: ['', Validators.required],
      secondoCtrl: ['', Validators.required],
      terzoCtrl: ['', Validators.required]
    });
  }

  onProponi(): void {
    if (!this.sceltaTagComponent) {
      console.log("errore nel metodo proponi");
      return;
    }
    const progetto: ProgettoDto = new ProgettoDto(this.nome, this.obiettivi, this.requisiti, this.sceltaTagComponent.getTagsScelti().tags);
    this.proponenteService.proponi(progetto).subscribe(
      (data: Progetto) => {
        this.idProgetto = data.id;
        if (this.invitaProgettistiComponent && this.invitaProgettistiComponent.progettistiInvitati.size > 0) {
          this.invitaProgettistiComponent.idProgetto = this.idProgetto;
          this.invitaProgettistiComponent.invitaProgettisti()?.subscribe(
            data => {
              this.toastr.success(data.messaggio, "OK", {
                timeOut: 3000, positionClass: "toast-top-center"
              });
              if (this.permettiValutazioneComponent)
                this.permettiValutazioneComponent.idProgetto = this.idProgetto;
              if (this.permettiValutazioneComponent && this.sceltaTagComponent)
                this.permettiValutazioneComponent.tags = this.sceltaTagComponent.getTagsScelti();
              this.permettiValutazioneComponent?.permettiValutazione();
            },
            err => {
              this.toastr.error(err.error.messaggio, "Errore", {
                timeOut: 3000, positionClass: "toast-top-center"
              });
            });
        } else {
          if (this.permettiValutazioneComponent)
            this.permettiValutazioneComponent.idProgetto = this.idProgetto;
          if (this.permettiValutazioneComponent && this.sceltaTagComponent)
            this.permettiValutazioneComponent.tags = this.sceltaTagComponent.getTagsScelti();
          this.permettiValutazioneComponent?.permettiValutazione();
        }
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
}
