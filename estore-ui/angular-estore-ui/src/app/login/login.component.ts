import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { AuthService } from '../auth.service';
import { AppComponent } from '../app.component';
import { Customer } from '../customer';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['../app.component.css']
})
export class LoginComponent {
  login: string = "";
  customer?: Customer;
  balance: number = 0;

  constructor(private http: HttpClient, private router :Router, private appComponent: AppComponent, private authService: AuthService){
  }

  ngOnInit(): void{
    
  }

  attemptLogin(login: string) {
      this.authService.login(login)
        .subscribe(customer => {
        this.customer = customer

        if (this.customer != null) {
          this.updateBalance(login);
          this.appComponent.setBalance(this.balance);
          this.appComponent.canDisplay = "yes";
          this.appComponent.setCookie(login);
          this.appComponent.showTitle = true;
          if (this.appComponent.login == 'admin') {
          this.router.navigate(["admin"]);
        }
        else {
          this.router.navigate(['customer'])
        }
      }
    });
  }

  updateBalance(login: string) {
    this.authService.login(login)
      .subscribe(customer => {
      this.balance = customer.balance
      this.appComponent.setBalance(this.balance);
    });
  }

}



