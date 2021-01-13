import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Progetto } from 'src/app/modello/progetto/progetto';
import { ProgettoService } from 'src/app/servizi/progetto.service';
import { Tag } from 'src/app/modello/progetto/tag';

@Component({
  selector: 'app-dettagli',
  templateUrl: './dettagli.component.html',
  styleUrls: ['./dettagli.component.css']
})
export class DettagliComponent implements OnInit {

  progetto?: Progetto; //= null as any;
  private colore: number = 0;
  constructor(private progettoService: ProgettoService, private activatedRoute: ActivatedRoute,
    private toastr: ToastrService) { }

  ngOnInit(): void {

    this.colore = Math.floor(Math.random() * Tag.colori.length + 1);
    const id: number = this.activatedRoute.snapshot.params.id;
    this.progettoService.getProgetto(id).subscribe(
      data => { this.progetto = data;
      console.log(this.progetto);},
      err => {
        this.toastr.error(err.error.messaggio, "Errore", {
          timeOut: 3000, positionClass: "toast-bottom-right"
        });
      }
    );
  }
  public getColore(i: number): string {
    return Tag.colori[(i + this.colore) % Tag.colori.length];
  }
}
