import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { LoginIscritto } from 'src/app/modello/login-iscritto';
import { AutenticazioneService } from 'src/app/servizi/autenticazione.service';
import { TokenService } from 'src/app/servizi/token.service';

@Component({
  selector: 'app-accedi',
  templateUrl: './accedi.component.html',
  styleUrls: ['./accedi.component.css']
})
export class AccediComponent implements OnInit {

  identificativo: string = null as any;
  password: string = null as any;
  loginIscritto: LoginIscritto = null as any;

  constructor(
    private tokenService: TokenService,
    private autenticazioneService: AutenticazioneService,
    private toastr: ToastrService,
    private router: Router) { }

  ngOnInit(): void {
  }

  onAccedi(): void {
    this.loginIscritto = new LoginIscritto(this.identificativo, this.password);
    this.autenticazioneService.accedi(this.loginIscritto).subscribe(
      data => {
        this.tokenService.setToken(data.token);
        this.toastr.success(`Bentornato ${this.tokenService.getIdentificativo()}!`, "Accesso effettuato", {
          timeOut: 3000, positionClass: "toast-top-center"
        });
        this.router.navigate(["/"]);
      },
      err => {
        if (!err.error.messaggio)
          err.error.messaggio = err.error.message;
        if (err.error.messaggio == "")
          err.error.messaggio = "Password errata";
        this.toastr.error(err.error.messaggio, "Errore nel login", {
          timeOut: 3000, positionClass: "toast-top-center"
        });
      });
  }
}
