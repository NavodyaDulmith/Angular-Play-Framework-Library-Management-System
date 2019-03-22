package models;


import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WestminsterLibraryManager implements LibraryManager {

        //connectin via url
        MongoClientURI uri = new MongoClientURI("mongodb://Navodya:mongo1234@cluster0-shard-00-00-wsbks.mongodb.net:27017,cluster0-shard-00-01-wsbks.mongodb.net:27017,cluster0-shard-00-02-wsbks.mongodb.net:27017/test?ssl=true&replicaSet=Cluster0-shard-0&authSource=admin&retryWrites=true");
        // Creating a Mongo client
        MongoClient mongo = new MongoClient(uri);

        // Accessing the database
        MongoDatabase database = mongo.getDatabase("Test");

        // Retrieving a collection
        MongoCollection<Document> bookCollection = database.getCollection("Book");
        MongoCollection<Document> dvdCollection = database.getCollection("DVD");

    public List<Book> getBooks(){

        List<Book> bookList = new ArrayList<>();
        FindIterable<Document> iterDoc = bookCollection.find();
        Iterator it = iterDoc.iterator();

        while (it.hasNext()) {

            Document d = (Document) it.next();
            long isbn = d.getLong("isbn");
            String title = d.getString("title");
            String sector = d.getString("sector");
            String publicationDate = d.getString("publication_date");

            int pageCount = d.getInteger("page_count");
            //reader details
            int readerID = d.getInteger("reader_id");
            String readerName = d.getString("reader_name");
            String readerMobileno = d.getString("reader_mobile_no");
            String readerEmali = d.getString("reader_email");

            //publisher details
            String publisher = d.getString("publisher_name");
            //author details
            String authorName = d.getString("author_name");

            int day = d.getInteger("day");
            int month = d.getInteger("month");
            int year = d.getInteger("year");
            int hour = d.getInteger("hour");
            int minute = d.getInteger("minute");

            bookList.add(new Book(isbn, title, sector, publicationDate, new Reader(readerID, readerName, readerMobileno, readerEmali), new DateTime(day, month, year, hour, minute), new Author(authorName), new Publisher(publisher), pageCount));
        }
         return bookList;

    }

    public List<DVD> getDvds(){
        List<DVD> dvdList = new ArrayList<>();

        FindIterable<Document> iterDoc = dvdCollection.find();
        Iterator it = iterDoc.iterator();

        while (it.hasNext()) {

            Document d = (Document) it.next();
            long isbn = d.getLong("isbn");
            String title = d.getString("title");
            String sector = d.getString("sector");
            String publicationDate = d.getString("publication_date");

            int readerID = d.getInteger("reader_id");
            String readerName = d.getString("reader_named");
            String readerMobileno = d.getString("reader_mobile_no");
            String readerEmali = d.getString("reader_email");

            int day = d.getInteger("day");
            int month = d.getInteger("month");
            int year = d.getInteger("year");
            int hour = d.getInteger("hour");
            int minute = d.getInteger("minute");

            String availableSub = d.getString("available_subtitles");
            String availableLan = d.getString("available_languages");

            String producerName = d.getString("producer_name");
            String actorNames = d.getString("actors");

            dvdList.add(new DVD(isbn,title,sector,publicationDate,new Reader(readerID,readerName,readerMobileno,readerEmali),
                    new DateTime(day,month,year,hour,minute),availableSub,availableLan,new Producer(producerName),actorNames));

        }

        return dvdList;

    }


    public void addBook() {

    }

    public void deleteItem() {


    }

    public void display() {

    }

    public void borrowAnItem() {

    }

    public void returnAnItem() {

    }

    public void generateReport() {

    }

}
