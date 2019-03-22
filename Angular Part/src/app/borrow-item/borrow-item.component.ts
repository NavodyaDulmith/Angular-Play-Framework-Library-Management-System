import { Component, OnInit } from '@angular/core';
import {Http} from '@angular/http';

@Component({
  selector: 'app-borrow-item',
  templateUrl: './borrow-item.component.html',
  styleUrls: ['./borrow-item.component.css']
})
export class BorrowItemComponent implements OnInit {

  isbn:String;
  readerID:string;
  readerName:string;
  readerMobileno:string;
  readerEmali:string;

  bDay:string;
  bMonth:string;
  bYear:string;
  bHour:string;
  bMin:string;

  
  
  constructor(private http: Http) { }

  persons;
  posts;
  res;

  ngOnInit() {
    this.http.get('http://localhost:9000/books').subscribe(
      response =>
      {
          this.persons=response.json();
          console.log(this.persons);

      }
    )
  }

  borrow()
  {
    let post={
      is:this.isbn,
      readerI:this.readerID,
      readerN:this.readerName,
      readerMobile:this.readerMobileno,
      readerEm:this.readerEmali, 
      borrowedDay:this.bDay,
      borrowedMonth:this.bMonth,
      borrowedYear:this.bYear,
      borrowedHour:this.bHour,
      borrowedMin:this.bMin,
      
    }

    console.log(post);

    this.http.post('http://localhost:9000/books/borrow',post).subscribe(
      response =>
      {
        
        console.log(response);
        
        this.posts=response.json();
        console.log(this.posts);
        
      }
    );
  }

  makeReservation(){

    let post={
      is:this.isbn,
      readerI:this.readerID,
      readerN:this.readerName,
      readerMobile:this.readerMobileno,
      readerEm:this.readerEmali, 
      borrowedDay:this.bDay,
      borrowedMonth:this.bMonth,
      borrowedYear:this.bYear,
      borrowedHour:this.bHour,
      borrowedMin:this.bMin,
      
    }

    console.log(post);

    this.http.post('http://localhost:9000/books/reservation',post).subscribe(
      response =>
      {
        
        console.log(response);
        
        this.res=response.json();
        console.log(this.res);
        
      }
    );

  }

}
