import { Component } from '@angular/core';
import { Product } from '../product';
import { ProductService } from '../product.service';
import { AppComponent } from '../app.component';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['../app.component.css']
})
export class AdminComponent {
  
  products: Product[] = [];
  selectedProduct?: Product;
  p = this.products;
  login: string = this.appComponent.login;
  filter: string = "";

  constructor(private productService: ProductService, private appComponent: AppComponent){
  }

  getProducts(): void {
    this.productService.getProducts()
    .subscribe(products => this.products = products);
  }

  search(): void {
    this.productService.searchproduct(this.filter).subscribe(products => this.products = products);
  }

  ngOnInit(): void {
    this.search();
  }

  addValueless(name: string): void {
    if (!name.trim()) { return; }
    let zero:number = 0;
    this.productService.addProduct({ name} as unknown as Product)
      .subscribe(product => {
        if (product != null) {
        this.products.push(product);
        console.log("price: " + product.price + " quantity: " + product.quantity);
        }
        else 
        console.log(product + " already exists.")
      });
  }

  add(name: string, price: string, quantity: string): void {
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

    console.log("New product added. Name: " + name + ", Price: " + price + ", Quantity: " + quantity);
    this.productService.addProduct({name : name, price : newprice, quantity : newquantity} as Product)
      .subscribe(product => {
        if (product != null) {
        this.products.push(product);
        }
        else 
        console.log(product + " already exists.")
      });
  }

  delete(product: Product): void {
    this.products = this.products.filter(oldProducts => oldProducts !== product);
    this.productService.deleteProduct(product.name).subscribe();
    if (this.selectedProduct?.name === product.name) {
      this.selectedProduct = undefined;
    }
  }

  save(product: Product): void {
    if (product.price <= 0 || product.quantity < 0) {
      window.alert("Price cannot be below or equal to 0. Quantity cannot be below 0.")
      return;
    }

    this.productService.updateProduct(product).subscribe();
  }

  onSelect(product: Product){
    this.selectedProduct = product;
  }
}
