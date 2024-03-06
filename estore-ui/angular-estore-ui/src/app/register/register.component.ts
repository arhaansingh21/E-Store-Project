import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router, NavigationExtras } from '@angular/router';
import { AppComponent } from '../app.component';
import { UserService } from '../user.service';
import { Customer } from '../customer';
import { AuthService } from '../auth.service';

@Component({
  selector: 'register',
  templateUrl: './register.component.html',
  styleUrls: ['../app.component.css']
})
export class RegisterComponent {
  login: string = "";
  customer?: Customer;

  constructor(private http: HttpClient, private router :Router, private appComponent: AppComponent, private authService: AuthService){

  }

  ngOnInit(): void{
  }

  attemptRegister(login: string) {
    this.authService.register(login)
      .subscribe(customer => {this.customer = customer; 
        if (this.customer != null) {
        this.router.navigate(['login'])
      }
      else 
      {
        window.alert("This login is already taken.")
      }
    });
  }

}
