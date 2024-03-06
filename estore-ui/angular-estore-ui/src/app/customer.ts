import { ShoppingCart } from './cart'

export interface Customer {
    id : string;
    shoppingCart : ShoppingCart;
    trade: number;
    balance: number;
  }