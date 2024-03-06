import { Component } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { AuthService } from './auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent {
  title = 'Game Store';
  login: string = "";
  showTitle: boolean = false;
  balance: number = 0;
  canDisplay: string = "";
  constructor(private cookie: CookieService, private authService: AuthService){

  }

  ngOnInit(): void{
    this.login = this.cookie.get("UserId");
    this.updateBalance(this.login);
    console.log("Get cookie " + this.login);
  }

  setCookie(login: string) {
    this.login = login;
    this.cookie.set("UserId",this.login);
    console.log("Set cookie " + this.login);
    window.document.body.style.background='ivory';
  }
  
  logout(): void {
    this.login = "";
    this.cookie.delete("UserId");
    this.cookie.delete("Balance");
    this.showTitle = false;
    window.document.body.style.backgroundImage="url(https://img.freepik.com/free-photo/vivid-blurred-colorful-background_58702-2655.jpg)";
    window.document.body.style.backgroundRepeat="no-repeat";
    window.document.body.style.backgroundSize="cover";
    console.log("Delete cookie " + this.login);
  }

  setBalance(balance: number) {
    this.balance = balance;
    this.cookie.set("Balance", this.balance.toString());
  }

  updateBalance(login: string) {
    this.authService.login(login)
      .subscribe(customer => {
      this.balance = customer.balance
    });
  }

}

