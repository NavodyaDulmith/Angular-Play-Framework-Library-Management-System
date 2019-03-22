package models;

import java.util.List;

public interface LibraryManager {

     void addBook();
     void deleteItem();
     void display();
     void borrowAnItem();
     void returnAnItem();
     void generateReport();
        List<Book> getBooks();
        List<DVD> getDvds();
}
