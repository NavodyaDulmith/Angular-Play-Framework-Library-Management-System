import { Component, OnInit } from '@angular/core';
import {Http} from '@angular/http';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {

  title:string;

  constructor(private http: Http) { }

  items;
  //posts;

  ngOnInit() {
   /*  this.http.get('http://localhost:9000/dvd').subscribe(
      response =>
      {
          this.posts=response.json();
          console.log(this.posts);
          
      }
      
    )
 */
  };

  searchItem(){
    let post={
      title:this.title,
      
    }
    console.log(post)

    this.http.post('http://localhost:9000/books/search',post).subscribe(
      response =>
      {
         console.log(response);

        this.items=response.json();
        console.log(this.items);
        
      }
    );

  }

}
