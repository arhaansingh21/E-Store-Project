import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms'; // <-- NgModel lives here
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ProductsComponent } from './products/products.component';
import { HttpClientModule } from '@angular/common/http';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component'; 
import { RouterModule } from '@angular/router';
import { AdminComponent } from './admin/admin.component';
import { ProductSearchComponent } from './product-search/product-search.component';
import { TradesComponent } from './trades/trades.component';
import { ShoppingCartComponent } from './shopping-cart/shopping-cart.component';
import { fas } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeModule, FaIconLibrary } from '@fortawesome/angular-fontawesome';
import { NavigationComponent } from './navigation/navigation.component';
import { CheckoutComponent } from './checkout/checkout.component';
import { NotfoundpageComponent } from './notfoundpage/notfoundpage.component';

@NgModule({
  imports: [
    AppRoutingModule,
    HttpClientModule, FontAwesomeModule, BrowserModule, FormsModule,ReactiveFormsModule, RouterModule.forRoot([
    { path: '', pathMatch: 'full', component: LoginComponent },
    { path: 'signup', component: RegisterComponent },
    { path: 'login', component: LoginComponent},
    { path: 'admin', component:AdminComponent},
    { path: 'home', component:ProductsComponent},
    { path: 'checkout', component:CheckoutComponent}
  ])],
  
  declarations: [
    AppComponent,
    ProductsComponent,
    LoginComponent,
    AdminComponent,
    RegisterComponent,
    AdminComponent,
    ProductSearchComponent,
    TradesComponent,
    ShoppingCartComponent,
    NavigationComponent,
    CheckoutComponent,
  ],

  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {  
  constructor(library: FaIconLibrary) {
    library.addIconPacks(fas);}
}
