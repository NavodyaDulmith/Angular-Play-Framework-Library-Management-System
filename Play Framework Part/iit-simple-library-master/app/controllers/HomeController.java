package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import models.*;
import org.bson.Document;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class HomeController extends Controller {

    LibraryManager libraryManager = new WestminsterLibraryManager();

    MongoClientURI uri = new MongoClientURI("mongodb://Navodya:mongo1234@cluster0-shard-00-00-wsbks.mongodb.net:27017,cluster0-shard-00-01-wsbks.mongodb.net:27017,cluster0-shard-00-02-wsbks.mongodb.net:27017/test?ssl=true&replicaSet=Cluster0-shard-0&authSource=admin&retryWrites=true");
    // Creating a Mongo client
    MongoClient mongo = new MongoClient(uri);
    // Accessing the database
    MongoDatabase database = mongo.getDatabase("Test");

    // Retrieving a collection
    MongoCollection<Document> bookCollection = database.getCollection("Book");
    MongoCollection<Document> dvdCollection = database.getCollection("DVD");
    MongoCollection<Document> reservationCollection = database.getCollection("Reservations");

    //list for store string valued json object
    List<String> returnOutput = new ArrayList<>();

    public Result index() {
        //call the interface and create obj for_list
        List<Book> bookList = libraryManager.getBooks();
        return ok(Json.toJson(bookList));
    }

    public Result getDVD() {
        //call the interface and create obj for_list
        List<DVD> dvdList = libraryManager.getDvds();
        return ok(Json.toJson(dvdList));
    }

    public Result addBook() {

        List<Book> bookList = libraryManager.getBooks();
        FindIterable<Document> iterDoc = bookCollection.find();
        Iterator it = iterDoc.iterator();
        FindIterable<Document> iterDocDVD = dvdCollection.find();
        Iterator itDVD = iterDocDVD.iterator();

        //try catch for check null
        try {
            //check bookList for 100 elements
            if (bookList.size() < 100) {

                JsonNode body = request().body().asJson();
                long isbn1 = Long.parseLong(body.get("is").asText());
                String title = body.get("tit").asText();
                String sector = body.get("sec").asText();
                String publicationDate = body.get("publication").asText();
                int pageCount = Integer.parseInt(body.get("totalPa").asText());
                String publisherfName = body.get("publisherfN").asText();
                String authorfName = body.get("authorfN").asText();

                //loop through book_collec
                while (it.hasNext()) {

                    returnOutput.clear();
                    Document bookQuery = (Document) it.next();
                    long isbn2 = bookQuery.getLong("isbn");

                    if (isbn1 == isbn2) {

                        returnOutput.clear();
                        returnOutput.add("Book is already Added to the System...!");
                        return ok(Json.toJson((returnOutput)));

                    }
                }
                //loop for dvd_collec
                while (itDVD.hasNext()) {

                    returnOutput.clear();
                    Document dvdQuery = (Document) itDVD.next();
                    long isbn2 = dvdQuery.getLong("isbn");

                    if (isbn1 == isbn2) {

                        returnOutput.clear();
                        returnOutput.add("Item already Added to the System...!");
                        return ok(Json.toJson((returnOutput)));

                    }
                }
                //add document to the collection
                Document book = new Document()
                        .append("isbn", isbn1)
                        .append("title", title)
                        .append("sector", sector)
                        .append("publication_date", publicationDate)
                        .append("page_count", pageCount)

                        .append("reader_id", 0)
                        .append("reader_name", "-")
                        .append("reader_mobile_no", "-")
                        .append("reader_email", "-")

                        .append("publisher_name", publisherfName)
                        .append("author_name", authorfName)

                        .append("day", 0)
                        .append("month", 0)
                        .append("year", 0)
                        .append("hour", 0)
                        .append("minute", 0);


                bookCollection.insertOne(book);

                returnOutput.clear();
                returnOutput.add("Book Successfully Added..!");
                return ok(Json.toJson((returnOutput)));

            }
        } catch (NullPointerException | InputMismatchException e) {

            returnOutput.clear();
            returnOutput.add("Please check All the Fields are filled in the Form !!!");
            return ok(Json.toJson((returnOutput)));

        }

        returnOutput.add("Storage Full..! 100 Books has Already Added to the System..!");
        return ok(Json.toJson((returnOutput)));

    }

    public Result delete(Long is) {

        //get collection
        FindIterable<Document> iterDoc = bookCollection.find();
        //creating iterators
        Iterator it = iterDoc.iterator();
        FindIterable<Document> iterDocDVD = dvdCollection.find();
        Iterator itDVD = iterDocDVD.iterator();

        //loop through book_collection
        while (it.hasNext()) {

            returnOutput.clear();
            Document bookQuery = (Document) it.next();
            long isbn2 = bookQuery.getLong("isbn");

            //check isbnN
            if (is == isbn2) {

                bookCollection.deleteOne(Filters.eq("isbn", is));
                returnOutput.add("Book Successfully Deleted..!");
                return ok(Json.toJson((returnOutput)));
            }
        }

        //loop through dvd_collection
        while (itDVD.hasNext()) {

            returnOutput.clear();
            Document dvdQuery = (Document) itDVD.next();
            long isbn2 = dvdQuery.getLong("isbn");

            if (is == isbn2) {

                dvdCollection.deleteOne(Filters.eq("isbn", is));
                returnOutput.clear();
                returnOutput.add("DVD Successfully Deleted..!");
                return ok(Json.toJson((returnOutput)));
            }
        }
        return ok();
    }

    public Result borrow() {
        //creating iterable for bookanddvd collection
        FindIterable<Document> findBook = bookCollection.find();
        Iterator bookIterator = findBook.iterator();
        FindIterable<Document> findDVD = dvdCollection.find();
        Iterator dvdIterator = findDVD.iterator();

        //try catch for check null
        try {
            JsonNode body = request().body().asJson();
            long isbn1 = Long.parseLong(body.get("is").asText());

            int readerID = Integer.parseInt(body.get("readerI").asText());
            String readerName = body.get("readerN").asText();
            String readerMobileno = body.get("readerMobile").asText();
            String readerEmali = body.get("readerEm").asText();

            int day = Integer.parseInt(body.get("borrowedDay").asText());
            int month = Integer.parseInt(body.get("borrowedMonth").asText());
            int year = Integer.parseInt(body.get("borrowedYear").asText());
            int hour = Integer.parseInt(body.get("borrowedHour").asText());
            int minute = Integer.parseInt(body.get("borrowedMin").asText());

            //loop for bookCollection
            while (bookIterator.hasNext()) {

                returnOutput.clear();
                Document bookQuery = (Document) bookIterator.next();
                long isbn = bookQuery.getLong("isbn");

                //get database value of readerId
                int rID1 = bookQuery.getInteger("reader_id");

                int day1 = bookQuery.getInteger("day");
                int month1 = bookQuery.getInteger("month");
                int year1 = bookQuery.getInteger("year");


                if (isbn == isbn1) {

                    if (day < day1 || month < month1 || year < year1) {
                        returnOutput.clear();
                        returnOutput.add("Invalid Date.. Please Re-Enter..!");
                        return ok(Json.toJson((returnOutput)));

                    }

                    if (rID1 == 0) {

                        bookQuery.append("isbn", isbn);

                        Document setbookData = new Document();

                        setbookData.append("reader_id", readerID).append("reader_name", readerName).append("reader_mobile_no", readerMobileno).append("reader_email", readerEmali)
                                .append("day", day)
                                .append("month", month)
                                .append("year", year)
                                .append("hour", hour)
                                .append("minute", minute);

                        Document updateBook = new Document();
                        updateBook.append("$set", setbookData);
                        //To update single Document
                        bookCollection.updateOne(bookQuery, updateBook);

                        returnOutput.clear();
                        returnOutput.add("Done..book");
                        return ok(Json.toJson((returnOutput)));

                    } else {

                        //assign to int date values as String date
                        String oldDate = (year1 + "-" + month1 + "-" + day1);

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Calendar c = Calendar.getInstance();
                        try {
                            c.setTime(sdf.parse(oldDate));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        //Incrementing the date by 7 day
                        c.add(Calendar.DAY_OF_MONTH, 7);
                        String newDate = sdf.format(c.getTime());

                        returnOutput.clear();
                        returnOutput.add("Book has been already borrowed.. will be available on " + newDate + " at  " + hour + " : " + minute);

                        return ok(Json.toJson((returnOutput)));
                    }

                }
            }
            //loop for dvdCollection
            while (dvdIterator.hasNext()) {

                returnOutput.clear();
                Document dvdQuery = (Document) dvdIterator.next();
                long dvdIsbn = dvdQuery.getLong("isbn");

                //get database value of readerId
                int dvdReaderid = dvdQuery.getInteger("reader_id");

                int day1 = dvdQuery.getInteger("day");
                int month1 = dvdQuery.getInteger("month");
                int year1 = dvdQuery.getInteger("year");

                if (dvdIsbn == isbn1) {

                    if (day < day1 || month < month1 || year < year1) {
                        returnOutput.clear();
                        returnOutput.add("Invalid Date.. Please Re-Enter..!");
                        return ok(Json.toJson((returnOutput)));

                    }

                    if (dvdReaderid == 0) {

                        dvdQuery.append("isbn", dvdIsbn);

                        Document setdvdData = new Document();

                        setdvdData.append("reader_id", readerID).append("reader_named", readerName).append("reader_mobile_no", readerMobileno).append("reader_email", readerEmali)
                                .append("day", day)
                                .append("month", month)
                                .append("year", year)
                                .append("hour", hour)
                                .append("minute", minute);

                        Document updateDvd = new Document();
                        updateDvd.append("$set", setdvdData);
                        //To update single Document
                        dvdCollection.updateOne(dvdQuery, updateDvd);

                        returnOutput.clear();
                        returnOutput.add("Done..dvd");
                        return ok(Json.toJson((returnOutput)));
                    } else {

                        String oldDate = (year1 + "-" + month1 + "-" + day1);

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Calendar c = Calendar.getInstance();
                        try {
                            c.setTime(sdf.parse(oldDate));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        //Incrementing the date by 7 day
                        c.add(Calendar.DAY_OF_MONTH, 3);
                        String newDate = sdf.format(c.getTime());

                        System.out.println("DVD has been already borrowed.. will be available on " + newDate + " at  " + hour + " : " + minute);
                        returnOutput.clear();
                        returnOutput.add("DVD has been already borrowed.. will be available on " + newDate + " at  " + hour + " : " + minute);

                        return ok(Json.toJson((returnOutput)));
                    }

                }
            }

            returnOutput.add("Invalid dvd ISBN Number,Please Re Enter...");
            return ok(Json.toJson((returnOutput)));

        } catch (NullPointerException | InputMismatchException e) {

            returnOutput.clear();
            returnOutput.add("Please check All the Fields are filled in the Form !!!");
            return ok(Json.toJson((returnOutput)));

        }

    }

    public Result returnItem() {

        //creating iterable
        FindIterable<Document> iterDoc = bookCollection.find();
        Iterator it = iterDoc.iterator();

        FindIterable<Document> findDVD = dvdCollection.find();
        Iterator dvdIterator = findDVD.iterator();
        //get data from body
        JsonNode body = request().body().asJson();
        int newDay = Integer.parseInt(body.get("borrowedDay").asText());
        int newMonth = Integer.parseInt(body.get("borrowedMonth").asText());
        int newYear = Integer.parseInt(body.get("borrowedYear").asText());
        int newHour = Integer.parseInt(body.get("borrowedHour").asText());
        int newMinute = Integer.parseInt(body.get("borrowedMin").asText());
        long isbn1 = Long.parseLong(body.get("is").asText());
        int readerID1 = Integer.parseInt(body.get("readerI").asText());

        while (it.hasNext()) {

            returnOutput.clear();
            Document bookQuery = (Document) it.next();

            long isbn = bookQuery.getLong("isbn");
            int readerID = bookQuery.getInteger("reader_id");

            if ((isbn == isbn1) && (readerID == readerID1)) {
                //get values from db
                int bDay = bookQuery.getInteger("day");
                int bMonth = bookQuery.getInteger("month");
                int bYear = bookQuery.getInteger("year");
                int bHour = bookQuery.getInteger("hour");
                int bMinute = bookQuery.getInteger("minute");

                //adding date obj for prevDates
                LocalDate dateBefore = LocalDate.of(bYear, bMonth, bDay);
                LocalDate dateAfter = LocalDate.of(newYear, newMonth, newDay);

                long noOfDaysBetween = ChronoUnit.DAYS.between(dateBefore, dateAfter);
                System.out.println(noOfDaysBetween);
                //check date limit
                if (noOfDaysBetween > 7) {

                    int hourCount = ((newMinute + (newHour * 60)) - (bMinute + (bHour * 60))) / 60;
                    noOfDaysBetween = noOfDaysBetween - 7;
                    double bookPayment1 = 0;
                    double bookPayment2 = 0;
                    //calculate fine
                    if (noOfDaysBetween <= 3) {

                        bookPayment1 = ((noOfDaysBetween) * 24 + hourCount) * 0.2;

                        returnOutput.clear();
                        returnOutput.add("You Have To Pay £" + (bookPayment1));
                        return ok(Json.toJson((returnOutput)));

                    } else {
                        bookPayment1 = ((noOfDaysBetween) * 24 + hourCount) * 0.2;
                        bookPayment2 = (bookPayment1 + ((noOfDaysBetween - 3) * 24 + hourCount) * 0.5);
                    }

                    returnOutput.clear();
                    returnOutput.add("You Have To Pay fine £" + bookPayment2);
                    return ok(Json.toJson((returnOutput)));


                } else {
                    //updating collection
                    bookQuery.append("isbn", isbn);
                    Document setData = new Document();
                    setData.append("reader_id", 0).append("reader_name", "-").append("reader_mobile_no", "-").append("reader_email", "-")
                            .append("day", 0)
                            .append("month", 0)
                            .append("year", 0)
                            .append("hour", 0)
                            .append("minute", 0);
                    Document update = new Document();
                    update.append("$set", setData);
                    //To update single Document
                    bookCollection.updateOne(bookQuery, update);

                    returnOutput.clear();
                    returnOutput.add("Updated.....");
                    return ok(Json.toJson((returnOutput)));

                }

            } else {
                returnOutput.add("Invalid ISBN Number or Reader ID,Please Re Enter...");
            }

        }

        while (dvdIterator.hasNext()) {

            returnOutput.clear();
            //get values from db
            Document dvdQuery = (Document) dvdIterator.next();
            long isbn = dvdQuery.getLong("isbn");
            int readerID = dvdQuery.getInteger("reader_id");

            if ((isbn == isbn1) && (readerID == readerID1)) {

                int borrowedDay = dvdQuery.getInteger("day");
                int borrowedMonth = dvdQuery.getInteger("month");
                int borrowedYear = dvdQuery.getInteger("year");
                int borrowedHour = dvdQuery.getInteger("hour");
                int borrowedMinute = dvdQuery.getInteger("minute");

                LocalDate dateBefore = LocalDate.of(borrowedDay, borrowedMonth, borrowedYear);
                LocalDate dateAfter = LocalDate.of(newYear, newMonth, newDay);

                long noOfDays = ChronoUnit.DAYS.between(dateBefore, dateAfter);
                System.out.println(noOfDays);
                //check for day limit 0f 3
                if (noOfDays > 3) {

                    returnOutput.clear();
                    int hourCount = ((newMinute + (newHour * 60)) - (borrowedMinute + (borrowedHour * 60))) / 60;
                    noOfDays = noOfDays - 3;
                    double payment1 = 0;
                    double payment2 = 0;
                    //calculation for not 3days
                    if (noOfDays <= 3) {

                        payment1 = ((noOfDays) * 24 + hourCount) * 0.2;
                        returnOutput.add("You Have To Pay £" + (payment1));
                        return ok(Json.toJson((returnOutput)));
                    //calculate fine
                    } else {
                        payment1 = ((noOfDays) * 24 + hourCount) * 0.2;
                        payment2 = (payment1 + ((noOfDays - 3) * 24 + hourCount) * 0.5);
                    }

                    returnOutput.clear();
                    returnOutput.add("You Have To Pay £" + payment2);
                    return ok(Json.toJson((returnOutput)));


                } else {
                    //updating collection
                    dvdQuery.append("isbn", isbn);
                    Document setData = new Document();
                    setData.append("reader_id", 0).append("reader_named", "-").append("reader_mobile_no", "-").append("reader_email", "-")
                            .append("day", 0)
                            .append("month", 0)
                            .append("year", 0)
                            .append("hour", 0)
                            .append("minute", 0);
                    Document update = new Document();
                    update.append("$set", setData);
                    //To update single Document
                    dvdCollection.updateOne(dvdQuery, update);

                    returnOutput.clear();
                    returnOutput.add("Updated.....");
                    return ok(Json.toJson((returnOutput)));

                }

            } else {
                returnOutput.add("Invalid ISBN Number or Reader ID,Please Re Enter...");
            }

        }

        return ok(Json.toJson((returnOutput)));
    }

    public Result addDvd() {

        List<DVD> dvdList = libraryManager.getDvds();
        //creating iterable
        FindIterable<Document> findBook = bookCollection.find();
        Iterator bookIterator = findBook.iterator();
        FindIterable<Document> findDVD = dvdCollection.find();
        Iterator dvdIterator = findDVD.iterator();
        //get data from body
        JsonNode body = request().body().asJson();
        long isbn1 = Long.parseLong(body.get("is").asText());
        String title = body.get("tit").asText();
        String sector = body.get("sec").asText();
        String publicationDate = body.get("publication").asText();
        String availableSub = body.get("availableSub").asText();
        String availableLan = body.get("availableLan").asText();
        String prodName = body.get("prodName").asText();
        String actName = body.get("actName").asText();

        //check null from exception handling
        try {
            //check dvdList size for 50 elements
            if (dvdList.size() < 50) {
                //finding any similar item in book_collection
                while (bookIterator.hasNext()) {

                    Document bookQuery = (Document) bookIterator.next();
                    long bookIsbn = bookQuery.getLong("isbn");

                    if (isbn1 == bookIsbn) {
                        //return ok("Book is already Added to System...!");
                        returnOutput.clear();
                        returnOutput.add("Item is already Added to the System...!");
                        return ok(Json.toJson((returnOutput)));

                    }
                }

                //finding any similar item in dvd_collection
                while (dvdIterator.hasNext()) {
                    returnOutput.clear();
                    Document dvdQuery = (Document) dvdIterator.next();
                    long dvdIsbn = dvdQuery.getLong("isbn");

                    if (isbn1 == dvdIsbn) {
                        //return ok("Book is already Added to System...!");
                        returnOutput.clear();
                        returnOutput.add("DVD is already Added to the System...!");
                        return ok(Json.toJson((returnOutput)));

                    }
                }
                //add document to the collection
                Document dvd = new Document()
                        .append("isbn", isbn1)
                        .append("title", title)
                        .append("sector", sector)
                        .append("publication_date", publicationDate)
                        .append("available_subtitles", availableSub)
                        .append("available_languages", availableLan)
                        .append("producer_name", prodName)
                        .append("actors", actName)

                        .append("reader_id", 0)
                        .append("reader_named", "-")
                        .append("reader_mobile_no", "-")
                        .append("reader_email", "-")

                        .append("day", 0)
                        .append("month", 0)
                        .append("year", 0)
                        .append("hour", 0)
                        .append("minute", 0);

                dvdCollection.insertOne(dvd);

                returnOutput.clear();
                returnOutput.add("DVD Successfully Added..!");
                return ok(Json.toJson((returnOutput)));
                //return ok();
            }
        } catch (NullPointerException | InputMismatchException e) {
            //return error
            returnOutput.clear();
            returnOutput.add("Please check All the Fields are filled in the Form !!!");
            return ok(Json.toJson((returnOutput)));

        }
        //return error
        returnOutput.add("Storage Full..! 100 Books has Already Added to the System..!");
        return ok(Json.toJson((returnOutput)));
    }

    public Result generateReport() {
        //call the lists
        List<DVD> dvdList = libraryManager.getDvds();
        List<Book> bookList = libraryManager.getBooks();
        returnOutput.clear();
        //looping through list
        for (int i = 0; i < bookList.size(); i++) {

            Book book = bookList.get(i);
            if (book.getCurrentReader().getReaderID() != 0) {
                //get borroweddatetime details
                Long isbn = book.getIsbn();
                int readerID = book.getCurrentReader().getReaderID();
                int day = book.getBorrowedDateTime().getDay();
                int month = book.getBorrowedDateTime().getMonth();
                int year = book.getBorrowedDateTime().getYear();
                int hour = book.getBorrowedDateTime().getHour();
                int minute = book.getBorrowedDateTime().getMinute();
                //get current datetime
                LocalDateTime x = LocalDateTime.now();
                int day1 = x.getDayOfMonth();
                int month1 = x.getMonthValue();
                int year1 = x.getYear();
                int h = x.getHour();
                int mi = x.getHour();

                int dateCount = 0;
                 //calculating date count
                dateCount = (day1 + (month1 * 31) + (year1 * 12 * 31)) - (day + (month * 31) + (year * 12 * 31));

                if (dateCount > 7) {

                    int hourCount = ((h + (mi * 60)) - (minute + (hour * 60))) / 60;
                    dateCount = dateCount - 7;
                    double bookPayment1 = 0;

                    if (dateCount <= 3) {
                        bookPayment1 = ((dateCount) * 24 + hourCount) * 0.2;

                    } else {
                        bookPayment1 = ((((dateCount) * 24 + hourCount) * 0.2) + ((dateCount - 3) * 24 + hourCount) * 0.5);
                    }

                    returnOutput.add("Item - " + isbn + " " + " Corresponding Fee - " + bookPayment1 + "£ " + " Reader ID - " + readerID);

                }
            }

        }
        for (int i = 0; i < dvdList.size(); i++) {

            DVD dvd = dvdList.get(i);
            if (dvd.getCurrentReader().getReaderID() != 0) {
                //get borroweddatetime details
                Long isbn = dvd.getIsbn();
                int readerID = dvd.getCurrentReader().getReaderID();
                int day = dvd.getBorrowedDateTime().getDay();
                int month = dvd.getBorrowedDateTime().getMonth();
                int year = dvd.getBorrowedDateTime().getYear();
                int hour = dvd.getBorrowedDateTime().getHour();
                int minute = dvd.getBorrowedDateTime().getMinute();
                //get current datetime
                LocalDateTime x = LocalDateTime.now();
                int day1 = x.getDayOfMonth();
                int month1 = x.getMonthValue();
                int year1 = x.getYear();
                int h = x.getHour();
                int mi = x.getHour();

                int dateCount;
                //calculating date count
                dateCount = (day1 + (month1 * 31) + (year1 * 12 * 31)) - (day + (month * 31) + (year * 12 * 31));

                if (dateCount > 3) {

                    int hourCount = ((h + (mi * 60)) - (minute + (hour * 60))) / 60;
                    dateCount = dateCount - 3;
                    double dvdPayment;

                    if (dateCount <= 3) {
                        dvdPayment = ((dateCount) * 24 + hourCount) * 0.2;

                    } else {
                        dvdPayment = ((((dateCount) * 24 + hourCount) * 0.2) + ((dateCount - 3) * 24 + hourCount) * 0.5);
                    }

                    returnOutput.add("Item - " + isbn + " " + " Corresponding Fee - " + dvdPayment + "£ " + " Reader ID - " + readerID);
                }
            }
        }
        return ok(Json.toJson(returnOutput));

    }

    public Result makeReservation() {

        //creating iterable
        FindIterable<Document> findBook = bookCollection.find();
        Iterator bookIterator = findBook.iterator();
        FindIterable<Document> findRes = reservationCollection.find();
        Iterator resIterator = findRes.iterator();
        FindIterable<Document> findDVD = dvdCollection.find();
        Iterator dvdIterator = findDVD.iterator();

        JsonNode body = request().body().asJson();
        long isbn1 = Long.parseLong(body.get("is").asText());

        int readerID = Integer.parseInt(body.get("readerI").asText());
        String readerName = body.get("readerN").asText();
        String readerMobileno = body.get("readerMobile").asText();
        String readerEmali = body.get("readerEm").asText();

        while (resIterator.hasNext()){
            Document resQuery = (Document) bookIterator.next();
            long isbn = resQuery.getLong("isbn");

            if(isbn==isbn1 ){

               int day = resQuery.getInteger("day");
               int month = resQuery.getInteger("month");
               int year = resQuery.getInteger("year");

                //assign to int date values as String date
                String oldDate = (year + "-" + month + "-" + day);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c = Calendar.getInstance();
                try {
                    c.setTime(sdf.parse(oldDate));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //Incrementing the date by 7 day
                c.add(Calendar.DAY_OF_MONTH, 7);
                String newDate = sdf.format(c.getTime());

                Document reserve = new Document()
                        .append("isbn", isbn1)

                        .append("reader_id", readerID)
                        .append("reader_name", readerName)
                        .append("reader_mobile_no", readerMobileno)
                        .append("reader_email", readerEmali)

                        .append("date", newDate);

                reservationCollection.insertOne(reserve);

                returnOutput.add("Reservation Successfully Added..!");
                return ok(Json.toJson((returnOutput)));
            }

        }

        //loop for bookCollection
        while (bookIterator.hasNext()) {

            returnOutput.clear();
            Document bookQuery = (Document) bookIterator.next();
            long isbn = bookQuery.getLong("isbn");

            if(isbn == isbn1){
                int day1 = bookQuery.getInteger("day");
                int month1 = bookQuery.getInteger("month");
                int year1 = bookQuery.getInteger("year");

                //assign to int date values as String date
                String oldDate = (year1 + "-" + month1 + "-" + day1);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c = Calendar.getInstance();
                try {
                    c.setTime(sdf.parse(oldDate));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //Incrementing the date by 7 day
                c.add(Calendar.DAY_OF_MONTH, 7);
                String newDate = sdf.format(c.getTime());

                Document reserve = new Document()
                        .append("isbn", isbn1)
                        .append("reader_id", readerID)
                        .append("reader_name", readerName)
                        .append("reader_mobile_no", readerMobileno)
                        .append("reader_email", readerEmali)

                        .append("date", newDate);

                reservationCollection.insertOne(reserve);

                returnOutput.add("Reservation_Successfully_Added..!");
                return ok(Json.toJson((returnOutput)));

            }

        }

        //loop for bookCollection
        while (dvdIterator.hasNext()) {

            int day1=0;
            int month1=0;
            int year1=0;

            returnOutput.clear();
            Document bookQuery = (Document) bookIterator.next();
            long isbn = bookQuery.getLong("isbn");

            if(isbn == isbn1){
                day1 = bookQuery.getInteger("day");
                month1 = bookQuery.getInteger("month");
                year1 = bookQuery.getInteger("year");

                //assign to int date values as String date
                String oldDate = (year1 + "-" + month1 + "-" + day1);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c = Calendar.getInstance();
                try {
                    c.setTime(sdf.parse(oldDate));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //Incrementing the date by 7 day
                c.add(Calendar.DAY_OF_MONTH, 7);
                String newDate = sdf.format(c.getTime());

                Document reserve = new Document()
                        .append("isbn", isbn1)

                        .append("reader_id", readerID)
                        .append("reader_name", readerName)
                        .append("reader_mobile_no", readerMobileno)
                        .append("reader_email", readerEmali)

                        .append("date", newDate);

                reservationCollection.insertOne(reserve);

                returnOutput.add("Reservation Successfully Added..!");
                return ok(Json.toJson((returnOutput)));

            }

        }
        return ok(Json.toJson((returnOutput)));

    }

    public Result searchItem(){

        FindIterable<Document> iterDoc = bookCollection.find();
        Iterator it = iterDoc.iterator();
        FindIterable<Document> iterDocDVD = dvdCollection.find();
        Iterator itDVD = iterDocDVD.iterator();

        JsonNode body = request().body().asJson();
        String title = body.get("title").asText();
        //loop through the collection
        while (it.hasNext()) {

            returnOutput.clear();
            Document bookQuery = (Document) it.next();
            String title2 = bookQuery.getString("title");
            //check the db value and the input value
            if (title.equals(title2)) {

                Long isbn = bookQuery.getLong("isbn");

                returnOutput.add("Book ISBN - "+isbn+" Title - "+title);
                return ok(Json.toJson((returnOutput)));
            }
        }

        while (itDVD.hasNext()) {

            returnOutput.clear();
            Document dvdQuery = (Document) itDVD.next();
            Document bookQuery = (Document) it.next();
            String title2 = bookQuery.getString("title");

            //check the db value and the input value
            if (title.equals(title2)) {

                Long isbn = dvdQuery.getLong("isbn");
                returnOutput.add("DVD ISBN - "+isbn+" Title - "+title);
                return ok(Json.toJson((returnOutput)));
            }
        }

        returnOutput.add("Not Fond Any Matches!!!");
        return ok(Json.toJson((returnOutput)));

    }

}
