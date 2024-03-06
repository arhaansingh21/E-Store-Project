import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminComponent } from './admin/admin.component';
import { LoginComponent } from './login/login.component';
import { ProductSearchComponent } from './product-search/product-search.component';
import { ProductsComponent } from './products/products.component';
import { RegisterComponent } from './register/register.component';
import { ShoppingCartComponent } from './shopping-cart/shopping-cart.component';
import { CheckoutComponent } from './checkout/checkout.component';
import { NotfoundpageComponent } from './notfoundpage/notfoundpage.component';
import { TradesComponent } from './trades/trades.component';

const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full'},
  { path: 'login', component: LoginComponent},
  { path: 'register', component:RegisterComponent},
  { path: 'customer', component: ProductsComponent},
  { path: 'admin', component:AdminComponent},
  { path: 'search' , component:ProductSearchComponent},
  { path: 'cart' , component:ShoppingCartComponent},
  { path: 'checkout', component:CheckoutComponent},
  { path: 'trades' , component:TradesComponent},
  {path: '404', component:NotfoundpageComponent},
  {path: '**', redirectTo: '/404'}
];
  
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
 