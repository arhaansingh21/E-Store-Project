import { Injectable } from '@angular/core';
import { Product } from './product';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private productUrl = 'http://localhost:8080/inventory/products'
  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(
    private http: HttpClient,
  ) { }

  searchproduct(name: string): Observable<Product[]> {
    if (!name.trim()) {
      return this.getProducts();
    }
    return this.http.get<Product[]>(`${this.productUrl}/search?name=${name}`).pipe(
      catchError(this.handleError<Product[]>('searchproduct', []))
    );
  }

  getProduct(name: string): Observable<Product> {
    const url = `${this.productUrl}/find?name=${name}`;
    return this.http.get<Product>(url).pipe(
      catchError(this.handleError<Product>(`getProduct name=${name}`))
    );
  }

  getProducts(): Observable<Product[]> {
    return this.http.get<Product[]>(this.productUrl)
      .pipe(
        catchError(this.handleError<Product[]>('getProducts', ))
      );
  }

  addProduct(Product: Product): Observable<Product> {
    return this.http.post<Product>(this.productUrl, Product, this.httpOptions).pipe(
      catchError(this.handleError<Product>('addProduct'))
    );
  }

  updateProduct(Product: Product): Observable<any> {
    return this.http.put(this.productUrl, Product, this.httpOptions).pipe(
      catchError(this.handleError<any>('updateProduct'))
    );
  }

  deleteProduct(name:string): Observable<Product> {
    const url = `${this.productUrl}/${name}`;

    return this.http.delete<Product>(url, this.httpOptions).pipe(
      catchError(this.handleError<Product>('deleteProduct'))
    );
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // TODO: better job of transforming error for user consumption

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }


}
