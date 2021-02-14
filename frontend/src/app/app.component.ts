import { DOCUMENT } from '@angular/common';
import { ChangeDetectorRef, Component, Inject, NgZone } from '@angular/core';
import { inject } from '@angular/core/testing';
import { NavigationEnd, NavigationStart, Router } from '@angular/router';
import { element } from 'protractor';
import { elementAt } from 'rxjs/operators';
import { LoaderService } from './servizi/loader.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'DoIt';
  loading = false;
  list?: NodeListOf<any>;

  constructor(
    @Inject(DOCUMENT) private _document: HTMLDocument,
    private ngZone: NgZone,
    private router: Router,
    private changeDetectorRef: ChangeDetectorRef,
    private loaderService: LoaderService) { }

  ngOnInit() {
    this.router.events.subscribe(event => {
      if (event instanceof NavigationStart){
        let btnCloseModal: any = this._document.querySelector('.modal-close-btn');
        btnCloseModal?.click();
      }
    });
    this.ngZone.runOutsideAngular(() => {
      this.loaderService.httpProgress().subscribe((status: boolean) => {
        this.loading = status;
        this.changeDetectorRef.detectChanges();
      });
    });
  }
}