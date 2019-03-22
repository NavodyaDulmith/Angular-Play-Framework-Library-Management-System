import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule} from '@angular/router';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { AddBookComponent } from './add-book/add-book.component';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { DisplayItemsComponent } from './display-items/display-items.component';
import { AddDvdComponent } from './add-dvd/add-dvd.component';
import { DeleteComponent } from './delete/delete.component';
import { BorrowItemComponent } from './borrow-item/borrow-item.component';
import { ReturnItemComponent } from './return-item/return-item.component';
import { ReportComponent } from './report/report.component';
import { SearchComponent } from './search/search.component';


@NgModule({
  declarations: [
    AppComponent,
    AddBookComponent,
    LoginComponent,
    HomeComponent,
    HeaderComponent,
    DisplayItemsComponent,
    AddDvdComponent,
    DeleteComponent,
    BorrowItemComponent,
    ReturnItemComponent,
    ReportComponent,
    SearchComponent,

    
 
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpModule,
    RouterModule.forRoot([
      {
        path:'home',
        component:HomeComponent
      },

      {
        path:'addBook',
        component:AddBookComponent
      },
      {
        path:'addDvd',
        component:AddDvdComponent
      },
      {
        path:'display',
        component:DisplayItemsComponent
      },
      {
        path:'delete',
        component:DeleteComponent
      },
      {
        path:'borrow',
        component:BorrowItemComponent
      },
      {
        path:'return',
        component:ReturnItemComponent
      },
      {
        path:'report',
        component:ReportComponent
      },
      {
        path:'search',
        component:SearchComponent
      },
      
      {
        path:'',
        component:LoginComponent
      }

    ])
  ],


  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
