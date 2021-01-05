import { Component, OnInit } from '@angular/core';
import { TokenService } from 'src/app/servizi/token.service';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {

  isLogged = false;

  constructor(private tokenService: TokenService) { }

  ngOnInit(): void {
    this.isLogged = this.tokenService.isLogged();
  }

  onDisconnettiti(): void {
    this.tokenService.disconnetti();
  }
}
