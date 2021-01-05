import { Component, OnInit } from '@angular/core';
import { TokenService } from 'src/app/servizi/token.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  identificativoIscritto: string|null = null;

  constructor(private tokenService: TokenService) { }

  ngOnInit(): void {
    this.identificativoIscritto = this.tokenService.getIdentificativo();
  }

}
