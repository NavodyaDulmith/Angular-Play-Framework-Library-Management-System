import { Component, OnInit } from '@angular/core';
import {Http} from '@angular/http';

@Component({
  selector: 'app-add-dvd',
  templateUrl: './add-dvd.component.html',
  styleUrls: ['./add-dvd.component.css']
})
export class AddDvdComponent implements OnInit {

  isbn:String;
  title: string;
  sector:string;
  publicationDate:string;
  producerName:string;
  availableSubtitles:string;
  availableLanguages:string;
  actorNames:string;

  
  constructor(private http: Http) { }

   persons;
   posts;
   freeSpace; 

  ngOnInit() {
    this.http.get('http://localhost:9000/dvd').subscribe(
      response =>
      {
        this.persons=response.json();
        console.log(this.persons);

        let x = (50 - (this.persons.length ));
        this.freeSpace=x;
        console.log(this.freeSpace)
        
        if(this.persons.length >50){
          console.log("Storage full");

        }
          
      }
    )
  }

  addDvd() {
      let post={
        is:this.isbn,
        tit:this.title,
        sec:this.sector,

        publication:this.publicationDate,

        prodName:this.producerName,       
        availableSub:this.availableSubtitles,
        availableLan:this.availableLanguages,
        actName:this.actorNames,
  
      }
      console.log(post);
      
      this.http.post('http://localhost:9000/dvd',post).subscribe(
        response =>
        {
           console.log(response);

          this.posts=response.json();
          console.log(this.posts);
          
        }
      );
  
  }
  
  
}
