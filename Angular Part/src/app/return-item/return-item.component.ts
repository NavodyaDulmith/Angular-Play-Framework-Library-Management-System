import { Component, OnInit } from '@angular/core';
import {Http} from '@angular/http';

@Component({
  selector: 'app-return-item',
  templateUrl: './return-item.component.html',
  styleUrls: ['./return-item.component.css']
})
export class ReturnItemComponent implements OnInit {

  isbn:String;
  readerID:string;

  bDay:string;
  bMonth:string;
  bYear:string;
  bHour:string;
  bMin:string;
  
  
  constructor(private http: Http) { }

  persons;
  posts;

  ngOnInit() {
    this.http.get('http://localhost:9000/books').subscribe(
      response =>
      {
          this.persons=response.json();
          console.log(this.persons);
          
          
      }
    )
  }

  returnItem()
  {
    let post={
      is:this.isbn,
      readerI:this.readerID,
      borrowedDay:this.bDay,
      borrowedMonth:this.bMonth,
      borrowedYear:this.bYear,
      borrowedHour:this.bHour,
      borrowedMin:this.bMin,
      
    }

    console.log(post);

    this.http.post('http://localhost:9000/books/return',post).subscribe(
      response =>
      {
        
        console.log(response);

        this.posts=response.json();
        console.log(this.posts);
        
      }
    );
  }

}