import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { RuoloDto } from 'src/app/modello-dto/iscritto-dto/ruolo/ruolo-dto';
import { RuoloOpzioni } from 'src/app/modello/iscritto/ruolo/ruolo-opzioni';
import { TipologiaRuolo } from 'src/app/modello/iscritto/ruolo/tipologia-ruolo.enum';
import { IscrittoService } from 'src/app/servizi/iscritto.service';
import { TokenService } from 'src/app/servizi/token.service';

@Component({
  selector: 'app-aggiungi-ruolo',
  templateUrl: './aggiungi-ruolo.component.html',
  styleUrls: ['./aggiungi-ruolo.component.css']
})
export class AggiungiRuoloComponent implements OnInit {

  ruoliDisponibili: RuoloOpzioni[] = [];

  constructor(
    private iscrittoService: IscrittoService,
    private tokenService: TokenService,
    private toastr: ToastrService) { }

  ngOnInit(): void {
    this.iscrittoService.ruoliDisponibili().subscribe(
      data => {
        data.forEach((tipo: TipologiaRuolo) => {
          this.ruoliDisponibili.push(RuoloOpzioni.RUOLI.get(tipo) as RuoloOpzioni);
        });
      },
      err => {
        console.log(err);
      }
    );
  }

  onAggiungiRuolo(ruolo: TipologiaRuolo): void {
    const r = new RuoloDto(ruolo);
    this.iscrittoService.aggiungiRuolo(r).subscribe(
      data => {
        this.toastr.success(data.messaggio, "Ruolo aggiunto", {
          timeOut: 3000, positionClass: "toast-top-center"
        });
        this.toastr.warning("Affinchè l'aggiunta del ruolo sia applicata, è necessario rieffettuare l'accesso.<br>Disconnessione in corso...", "Ruolo aggiunto - Attenzione!", {
          timeOut: 6000, positionClass: "toast-top-center",
          enableHtml:  true
        });
        this.tokenService.disconnetti();
      },
      err => {
        this.toastr.error(err.error.messaggio, "Errore", {
          timeOut: 3000, positionClass: "toast-top-center"
        });
      });
    }
}
