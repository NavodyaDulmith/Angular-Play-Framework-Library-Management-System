import { Component, OnInit } from '@angular/core';
import { Http } from '@angular/http';



@Component({
  selector: 'app-display-items',
  templateUrl: './display-items.component.html',
  styleUrls: ['./display-items.component.css']

})

export class DisplayItemsComponent implements OnInit {

  posts;
  books;


  constructor(private http:Http) { }



  ngOnInit() {
    this.http.get('http://localhost:9000/books').subscribe(
      response =>
      {
        this.books=response.json();
        console.log(this.books);
        
      }
    )
    this.http.get('http://localhost:9000/dvd').subscribe(
      response =>
      {
        this.posts=response.json();
        console.log(this.posts);
      }
    )


  }

}
