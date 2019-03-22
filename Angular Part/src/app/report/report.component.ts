import { Component, OnInit } from '@angular/core';
import { Http } from '@angular/http';

@Component({
  selector: 'app-report',
  templateUrl: './report.component.html',
  styleUrls: ['./report.component.css']
})
export class ReportComponent implements OnInit {
  books;

  constructor(private http:Http) { }

  ngOnInit() {
    this.http.get('http://localhost:9000/report').subscribe(
      response =>
      {
        this.books=response.json();
        console.log(this.books);
      }
    )
    this.http.get('http://localhost:9000/dvd').subscribe(
      
    )
  }


}
