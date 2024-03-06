import { Component } from '@angular/core';
import { UserService } from '../user.service';
import { AppComponent } from '../app.component';
import { Product } from '../product';
import { ProductService } from '../product.service';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['../app.component.css']
})
export class CheckoutComponent {
  login = this.appComponent.login;
  cartContents: Product[] = [];
  total: number = 0;
  products: Product[] = []; 
  constructor(private productService: ProductService, private userService: UserService, private appComponent: AppComponent) {
  }

  ngOnInit(): void {
    this.getShoppingCartContents()
  }

  getShoppingCartContents(): void {
    this.userService.getCart(this.login).subscribe(cartContents => {
      this.cartContents = cartContents.products;
      this.productService.getProducts().subscribe(products => {
        this.products = products;
  
        for (let i = 0; i < this.cartContents.length; i++) {
          let firstProduct = this.cartContents[i];
  
          // find matching product in second array
          let secondProduct = this.products.find(p =>
            p.name === firstProduct.name && p.price === firstProduct.price
          );
  
          // if found, compare quantities and update if necessary
          if (secondProduct && secondProduct.quantity < firstProduct.quantity) {
            firstProduct.quantity = secondProduct.quantity;
          }
        }
        this.updateTotal();
      });
    });
  }

  updateTotal(): void {
    let temp = 0
    for(var product of this.cartContents){
      temp += product.price * product.quantity;
    }
    this.total = temp;
  }

  updateInventory(product: Product): void {
    this.productService.getProduct(product.name).subscribe(p => {
      if (product) {
        p.quantity -= product.quantity;
        this.productService.updateProduct(p).subscribe();
      }});
  }

  removeProduct(product: Product) : void {
    this.userService.removeFromCart(this.login, product).subscribe();
    this.cartContents = this.cartContents.filter(oldProducts => oldProducts !== product);
  }

  removeItems(): void {
    for(var product of this.cartContents){
      this.updateInventory(product);
      this.removeProduct(product);
      this.getShoppingCartContents()
    }
    if (this.total >= this.appComponent.balance) {
      this.appComponent.setBalance(0);
    }
    else {
      let temp = this.appComponent.balance - this.total;
      this.appComponent.setBalance(temp);
    }
  }

  getBalance(): number {
    return this.appComponent.balance;
  }
}
