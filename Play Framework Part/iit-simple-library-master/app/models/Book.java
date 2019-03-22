package models;


public class Book extends LibraryItem{

    private Author author;
    private Publisher publisher;
    private int pageCount;

    public Book(long isbn, String title, String sector, String publicationDate, Reader currentReader, DateTime borrowedDateTime, Author author, Publisher publisher, int pageCount) {
        super(isbn, title, sector, publicationDate, currentReader, borrowedDateTime);
        this.author = author;
        this.publisher = publisher;
        this.pageCount = pageCount;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Book{" +
                "pageCount=" + pageCount +
                ", publisher=" + publisher +
                ", author=" + author +
                '}';
    }
}
