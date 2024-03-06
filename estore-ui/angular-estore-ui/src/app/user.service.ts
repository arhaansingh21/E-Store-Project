import { Injectable } from '@angular/core';
import { Customer } from './customer';
import { Observable, of } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Product } from './product';
import { ShoppingCart } from './cart';
import { Status, Trade } from './trade';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private customerUrl = 'http://localhost:8080/home'
  private tradeUrl = 'http://localhost:8080/home/trades'

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) { }

  addToCart(name: string, product:Product): Observable<Product> {
    return this.http.post<Product>(`${this.customerUrl}/add?id=${name}`, product, this.httpOptions).pipe(
      catchError(this.handleError<Product>('Product'))
    );
  }

  addToCartQuantity(name: string, product:Product, quantity:number): Observable<Product> {
    return this.http.post<Product>(`${this.customerUrl}/add?id=${name}&quantity=${quantity}`, product, this.httpOptions).pipe(
      catchError(this.handleError<Product>('Product'))
    );
  }

  deleteCustomer(name:string): Observable<Customer> {
    const url = `${this.customerUrl}/${name}`;

    return this.http.delete<Customer>(url, this.httpOptions).pipe(
      catchError(this.handleError<Customer>('deleteCustomer'))
    );
  }

  removeFromCart(name: string, product:Product): Observable<Product> {
    return this.http.post<Product>(`${this.customerUrl}/remove?id=${name}`, product, this.httpOptions).pipe(
      catchError(this.handleError<Product>('Product'))
    );
  }

  removeFromCartQuantity(name: string, product:Product, quantity:number): Observable<Product> {
    return this.http.post<Product>(`${this.customerUrl}/remove?id=${name}&quantity=${quantity}`, product, this.httpOptions).pipe(
      catchError(this.handleError<Product>('Product'))
    );
  }

  getCart(name : string): Observable<ShoppingCart> {
    return this.http.get<ShoppingCart>(`${this.customerUrl}/shoppingcart?id=${name}`, this.httpOptions).pipe(
      catchError(this.handleError<ShoppingCart>('ShoppingCart'))
    );
  }

  getTrades(name: string): Observable<Map<string, Trade[]>> {
    return this.http.get(`${this.tradeUrl}?id=${name}`, this.httpOptions)
      .pipe(
        map(response => {
          const tradesJson = JSON.parse(JSON.stringify(response)); // parse and stringify to ensure that the response is a plain object
          const tradesMap = new Map<string, Trade[]>();
          for (const key in tradesJson) {
            if (tradesJson.hasOwnProperty(key) && (tradesJson[key] instanceof Array)) {
              tradesMap.set(key, tradesJson[key]);
            }
          }
          return tradesMap;
        }),
        catchError(this.handleError<Map<string, Trade[]>>("Trades"))
      );
  }

  getAllTrades(): Observable<Map<string, Trade[]>> {
    return this.http.get(`${this.tradeUrl}/all`, this.httpOptions)
      .pipe(
        map(response => {
          const tradesJson = JSON.parse(JSON.stringify(response)); // parse and stringify to ensure that the response is a plain object
          const tradesMap = new Map<string, Trade[]>();
          for (const key in tradesJson) {
            if (tradesJson.hasOwnProperty(key) && (tradesJson[key] instanceof Array)) {
              tradesMap.set(key, tradesJson[key]);
            }
          }
          return tradesMap;
        }),
        catchError(this.handleError<Map<string, Trade[]>>("Trades"))
      );
  }

  addTrade(name: string, product:Product): Observable<Trade> {
    return this.http.post<Trade>(`${this.tradeUrl}/add?id=${name}`, product, this.httpOptions).pipe(
      catchError(this.handleError<Trade>('Trade'))
    );
  }

  changeTradeStatus(name: string, trade:Trade, status: Status): Observable<Trade> {
    return this.http.put<Trade>(`${this.tradeUrl}/status/string?id=${name}&status=${status}`, trade, this.httpOptions).pipe(
      catchError(this.handleError<Trade>('Trade'))
    );
  }

  modify(name: string, product: Product): Observable<Product> {
    return this.http.put<Product>(`${this.customerUrl}/modify?id=${name}`, product, this.httpOptions).pipe(
      catchError(this.handleError<Product>('Product'))
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
