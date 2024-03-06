import { ChangeDetectorRef, Component } from '@angular/core';
import { UserService } from '../user.service';
import { Product } from '../product';
import { AppComponent } from '../app.component';
import { Status, Trade } from '../trade';
import { NgZone } from '@angular/core';

@Component({
  selector: 'app-trades',
  templateUrl: './trades.component.html',
  styleUrls: ['../app.component.css']
})
export class TradesComponent {
  trades: Map<string, Trade[]> = new Map<string, Trade[]>();
  login = this.appComponent.login;
  showEmpty = false;

  constructor( private userService: UserService, private appComponent: AppComponent, private NgZone: NgZone, private cdr: ChangeDetectorRef) {
  }

  ngOnInit(): void {
    if (this.login === 'admin') {
      this.getAllTrades()
    }
    else { 
      this.getTrades()
    }
  }

  getTrades(): void {
    this.userService.getTrades(this.login).subscribe(trades => this.trades = trades);
  }

  getAllTrades(): void {
    this.userService.getAllTrades().subscribe(trades => this.trades = trades);
  }

  addTrade(name: string, price: string, quantity: string): void {
    let newprice = parseFloat(price)
    let newquantity = parseInt(quantity)

    if (!name.trim()) {
      window.alert("Please enter the name of the product you are selling.")
      return;
    }

    if (newprice <= 0 || newquantity < 0) {
      window.alert("Price cannot be below or equal to 0. Quantity cannot be below 0.")
      return;
    }

    console.log("New trade added. Name: " + name + ", Price: " + price + ", Quantity: " + quantity);
    this.userService.addTrade(this.login, {name : name, price : newprice, quantity : newquantity} as Product)
      .subscribe(trade => {
        if (this.trades != null) {
          let tradeArray = this.trades.get(this.login);
          tradeArray?.push(trade);
          this.trades.set(this.login, tradeArray || []);
        }
      });
  }

  cancelTrade(trade: Trade) {
    this.userService.changeTradeStatus(this.login, trade, Status.CANCELED).subscribe(response => 
      this.NgZone.run(() => {
        trade.status = response.status;
        this.cdr.detectChanges();
      }));
  }

  denyTrade(user: string, trade: Trade) {
    this.userService.changeTradeStatus(user, trade, Status.DENIED).subscribe(response => 
      this.NgZone.run(() => {
        trade.status = response.status;
        this.cdr.detectChanges();
      }));
  }
  
  approveTrade(user: string, trade: Trade) {
    this.userService.changeTradeStatus(user, trade, Status.ACCEPTED).subscribe(response => 
      this.NgZone.run(() => {
        trade.status = response.status;
        this.cdr.detectChanges();
      }));
  }

  isValid(key: string, value: any[]): boolean {
    return this.login === 'admin' && !value.every(trade => trade.status !== 'PEDNING');
  }
}
