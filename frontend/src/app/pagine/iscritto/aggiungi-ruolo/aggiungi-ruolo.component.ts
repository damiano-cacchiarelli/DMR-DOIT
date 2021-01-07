import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Ruolo } from 'src/app/modello/iscritto/ruolo';
import { RuoloOpzioni } from 'src/app/modello/iscritto/ruolo-opzioni';
import { TipologiaRuolo } from 'src/app/modello/iscritto/tipologia-ruolo.enum';
import { IscrittoService } from 'src/app/servizi/iscritto.service';

@Component({
  selector: 'app-aggiungi-ruolo',
  templateUrl: './aggiungi-ruolo.component.html',
  styleUrls: ['./aggiungi-ruolo.component.css']
})
export class AggiungiRuoloComponent implements OnInit {

  ruoliDisponibili: RuoloOpzioni[] = [];

  constructor(
    private iscrittoService: IscrittoService,
    private toastr: ToastrService,
    private router: Router) { }

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

  onAggiungiRuolo(ruolo: TipologiaRuolo) {
    const r = new Ruolo(ruolo);
    console.log(r);
    this.iscrittoService.aggiungiRuolo(r).subscribe(
      data => {
        this.toastr.success(data.messaggio, "Ruolo aggiunto", {
          timeOut: 3000, positionClass: "toast-top-center"
        });
        this.router.navigate(["/"]);
      },
      err => {
        this.toastr.error(err.error.messaggio, "Errore", {
          timeOut: 3000, positionClass: "toast-top-center"
        });
      });
  }
}
