import { AfterContentInit, AfterViewInit, ChangeDetectorRef, Component, NgZone } from '@angular/core';
import { LoaderService } from './servizi/loader.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'DoIt';
  loading = false;

  constructor(
    private ngZone: NgZone,
    private changeDetectorRef: ChangeDetectorRef,
    private loaderService: LoaderService) { }

  ngOnInit() {
    this.ngZone.runOutsideAngular(() => {
      this.loaderService.httpProgress().subscribe((status: boolean) => {
        console.log(status);
        this.loading = status;
        this.changeDetectorRef.detectChanges();
      });
    });
  }
}
