import { Component, OnInit } from '@angular/core';
import {Http} from '@angular/http';

@Component({
  selector: 'app-delete',
  templateUrl: './delete.component.html',
  styleUrls: ['./delete.component.css']
})
export class DeleteComponent implements OnInit {

  isbn:String;
  
  constructor(private http: Http) { }

  books;
  posts;
  dvds;
  bookSpace; 
  dvdSpace;

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
        this.dvds=response.json();
        console.log(this.dvds);

        let x = (50 - (this.dvds.length ));
        this.dvdSpace=x;
        console.log(this.dvdSpace)
          
      }
    )
    this.http.get('http://localhost:9000/books').subscribe(
      response =>
      {
          this.books=response.json();
          console.log(this.books);

          let y = (100 - (this.books.length ));
          this.bookSpace=y;
          console.log(this.bookSpace)
          
      }
      
    )

  }

  delete()
  {
    let post=this.isbn;
    //let x=1001;
    console.log(post);
    //delete from person array
    this.http.delete('http://localhost:9000/books/'+post).subscribe(
      response =>
      {
        
        console.log(response);
        this.posts=response.json();
        console.log(this.posts);
        
      }
    );
    
  }

}
