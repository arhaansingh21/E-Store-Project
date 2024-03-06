import { Component } from '@angular/core';
import { ProductService } from '../product.service';
import { UserService } from '../user.service';
import { Product } from '../product';
import { AppComponent } from '../app.component';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['../app.component.css']
})

export class ProductsComponent {
  products: Product[] = []; 
  login = this.appComponent.login;
  filter: string = "";

  constructor(private ProductService: ProductService, private userService: UserService, private appComponent: AppComponent) {
  }

  ngOnInit(): void {
    this.search();
  }

  getProducts(): void {
    this.ProductService.getProducts().subscribe(products => this.products = products);
  }

  addProductValueless(product: Product): void {
    this.userService.addToCartQuantity(this.login, product, 1).subscribe();
  }

  addProduct(product: Product, quantity: string): void {
    let newquantity = parseInt(quantity)
    if (newquantity && newquantity > 0) {
      this.userService.addToCartQuantity(this.login, product, newquantity).subscribe();
    }
    else this.addProductValueless(product);
  }

  search(): void {
    this.ProductService.searchproduct(this.filter).subscribe(products => this.products = products);
  }
}
