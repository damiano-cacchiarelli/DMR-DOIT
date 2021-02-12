import { AfterViewInit, Component } from '@angular/core';
import { LoaderService } from './servizi/loader.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements AfterViewInit {
  title = 'DoIt';
  loading = false;
  constructor(private loaderService: LoaderService) { }

  ngAfterViewInit() {
    this.loaderService.httpProgress().subscribe((status: boolean) => {
      console.log("ciao");
      this.loading = status;
    });
  }
}
