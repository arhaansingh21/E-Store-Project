import { Component } from '@angular/core';
import { Product } from '../product';
import { UserService } from '../user.service';
import { AppComponent } from '../app.component';

@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['../app.component.css']
})
export class ShoppingCartComponent {
  cartContents: Product[] = []
  login = this.appComponent.login;

  constructor( private userService: UserService, private appComponent: AppComponent) {
  }

  ngOnInit(): void {
    this.getShoppingCartContents();
  }

  getShoppingCartContents(): void {
    this.userService.getCart(this.login).subscribe(cartContents => this.cartContents = cartContents.products);
  }

  removeProduct(product: Product) : void {
    this.userService.removeFromCart(this.login, product).subscribe();
    this.cartContents = this.cartContents.filter(oldProducts => oldProducts !== product);
  }

  removeProductQuantity(product: Product, quantity: string) : void {
    let newquantity = parseInt(quantity)
    if (newquantity && newquantity > 0) {
      this.userService.removeFromCartQuantity(this.login, product, newquantity).subscribe();
    }
    else {
      this.userService.removeFromCart(this.login, product).subscribe();
      this.cartContents = this.cartContents.filter(oldProducts => oldProducts !== product);
    }
  }

  modify(product: Product) {
    if (product.quantity && product.quantity >= 0) {
      this.userService.modify(this.login, product).subscribe();
    }
    else this.removeProduct(product);
  }
}
