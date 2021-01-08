import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ProgettoDettagliato } from 'src/app/modello/progetto/progetto-dettagliato';
import { ProgettoService } from 'src/app/servizi/progetto.service';

@Component({
  selector: 'app-dettagli',
  templateUrl: './dettagli.component.html',
  styleUrls: ['./dettagli.component.css']
})
export class DettagliComponent implements OnInit {

  progetto?: ProgettoDettagliato ; //= null as any;

  constructor(private progettoService: ProgettoService, private activatedRoute: ActivatedRoute,
    private toastr: ToastrService) { }

  ngOnInit(): void {

    const id = this.activatedRoute.snapshot.params.id;
    this.progettoService.getProgetto(id).subscribe(
      data => { this.progetto = data },
      err => {
        this.toastr.error(err.error.messaggio, "Errore", {
          timeOut: 3000, positionClass: "toast-bottom-right"
        });
      }
    );
  }
}
