import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Invito } from 'src/app/modello/invito/invito';
import { RuoloSoggetto } from 'src/app/modello/invito/ruolo-soggetto.enum';
import { TipologiaInvito } from 'src/app/modello/invito/tipologia-invito.enum';
import { TipologiaRisposta } from 'src/app/modello/invito/tipologia-risposta.enum';
import { Progetto } from 'src/app/modello/progetto/progetto';
import { InvitoService } from 'src/app/servizi/invito.service';

@Component({
  selector: 'app-dettagli-invito',
  templateUrl: './dettagli-invito.component.html',
  styleUrls: ['./dettagli-invito.component.css']
})
export class DettagliInvitoComponent implements OnInit {

  invito?: Invito;
  TipologiaRisposta = TipologiaRisposta;
  TipologiaInvito = TipologiaInvito;

  constructor(
    private activatedRoute: ActivatedRoute,
    private invitoService: InvitoService,
    private toastr: ToastrService) { }

  ngOnInit(): void {
    this.invito = new Invito("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi suscipit, mauris vitae feugiat porttitor, est lorem luctus nibh, in iaculis eros lectus elementum velit. Donec eu vestibulum elit. Duis nec posuere nunc. Aliquam felis felis, ultrices vel semper quis, vulputate in purus. Ut vel dignissim turpis. Ut ultrices in lectus vel tempor. Nam velit erat, pellentesque eget commodo ut, efficitur vel libero. Morbi augue nibh, sodales sed enim sit amet, accumsan dapibus justo. Integer lacinia iaculis malesuada. Curabitur imperdiet sapien in sem bibendum, sit amet elementum metus varius. Mauris ut molestie ligula. Donec vitae congue nulla. Aliquam tristique, leo vel faucibus pulvinar, arcu libero pharetra nisl, quis iaculis quam massa id urna. Morbi sit amet ipsum metus.", TipologiaInvito.PROPOSTA, "mat", 1, "mat1", RuoloSoggetto.DESTINATARIO, new Date(), TipologiaRisposta.IN_ATTESA, "dam", new Progetto("Nome progetto", "", "", [], 1, null as any, null as any, null as any, "", null as any))

    /*const id = this.activatedRoute.snapshot.params.id;
    this.invitoService.getInvito(id).subscribe(
      data => this.invito = data,
      err => {
        this.toastr.error(err.error.messaggio, "Errore", {
          timeOut: 3000, positionClass: "toast-bottom-right"
        });
      }
    );*/

  }

  onElimina(id: string): void {
    console.log("elimina invito ", id);
  }

  onAccetta(id: string): void {
    console.log("accetta invito ", id);
  }

  onRifiuta(id: string): void {
    console.log("rifiuta invito ", id);
  }
}
