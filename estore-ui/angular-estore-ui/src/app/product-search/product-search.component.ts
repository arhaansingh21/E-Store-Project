import { Component, OnInit } from '@angular/core';

import { Observable, Subject } from 'rxjs';

import { Product } from '../product';
import { ProductService } from '../product.service';

@Component({
  selector: 'app-product-search',
  templateUrl: './product-search.component.html',
  styleUrls: ['../app.component.css']
})
export class ProductSearchComponent implements OnInit {
  //products$!: Observable<Product[]>;

  constructor(private productService: ProductService) {}

  // Push a search term into the observable stream.

  ngOnInit(): void {
  }
}