import { Component, OnInit } from '@angular/core';
import {Http} from '@angular/http';

@Component({
  selector: 'app-add-book',
  templateUrl: './add-book.component.html',
  styleUrls: ['./add-book.component.css']
})
export class AddBookComponent implements OnInit {

  isbn:String;
  title: string;
  sector:string;
  publicationDate:string;
  totalPages:string;
  publisherfName:string;
  authorfName:string;

  
  constructor(private http: Http) { }

   persons;
   posts;

   freeSpace;
    

  ngOnInit() {
    this.http.get('http://localhost:9000/books').subscribe(
      response =>
      {
          this.persons=response.json();
          console.log(this.persons);

          let x = (100 - (this.persons.length ));
          this.freeSpace=x;
          console.log(this.freeSpace)
          
          if(this.persons.length >100){
            console.log("Storage full");

          }
          
      }
      
    )
  }

  addbook() {

      let post={
        is:this.isbn,
        tit:this.title,
        sec:this.sector,
        publication:this.publicationDate,
        totalPa:this.totalPages,

        publisherfN:this.publisherfName,       
        authorfN:this.authorfName,
  
      }
      console.log(post);
      
      this.http.post('http://localhost:9000/books',post).subscribe(
        response =>
        {
           console.log(response);

          this.posts=response.json();
          console.log(this.posts);
          
        }
      );
  
  }
  
  
}
