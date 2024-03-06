import { Component } from '@angular/core';
import { AppComponent } from '../app.component';
import { ActivatedRoute, Router, NavigationEnd } from '@angular/router';

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['../app.component.css']
})
export class NavigationComponent {
  login = this.appComponent.login;
  currentRoute = this.router.url;
  
  constructor(private appComponent: AppComponent, private router: Router, private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.currentRoute = this.router.url;
    this.currentRoute = this.currentRoute.toLowerCase();
    this.login = this.appComponent.login;

    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.login = this.appComponent.login;
      }
    });

    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.currentRoute = this.router.url;
        this.currentRoute = this.currentRoute.toLowerCase();
      }
    });
  }

  logout(): void {
    this.appComponent.logout();
    this.router.navigateByUrl("/login");
  }
}
