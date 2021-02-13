import { ChangeDetectorRef, Component, NgZone } from '@angular/core';
import { NavigationEnd, NavigationStart, Router } from '@angular/router';
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
    private router: Router,
    private changeDetectorRef: ChangeDetectorRef,
    private loaderService: LoaderService) { }

  ngOnInit() {
    this.router.events.subscribe(event => {
      if(event instanceof NavigationStart)
        console.log("modal sevice dismiss all");
    });
    this.ngZone.runOutsideAngular(() => {
      this.loaderService.httpProgress().subscribe((status: boolean) => {
        this.loading = status;
        this.changeDetectorRef.detectChanges();
      });
    });
  }
}
