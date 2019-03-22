package models;

public abstract class LibraryItem {
    private long isbn;
    private String title;
    private String sector;
    private String publicationDate;
    private Reader currentReader;
    private DateTime borrowedDateTime;

    public LibraryItem(long isbn, String title, String sector, String publicationDate, Reader currentReader, DateTime borrowedDateTime) {
        this.isbn = isbn;
        this.title = title;
        this.sector = sector;
        this.publicationDate = publicationDate;
        this.currentReader = currentReader;
        this.borrowedDateTime = borrowedDateTime;
    }

    public long getIsbn() {
        return isbn;
    }

    public void setIsbn(long isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public Reader getCurrentReader() {
        return currentReader;
    }

    public void setCurrentReader(Reader currentReader) {
        this.currentReader = currentReader;
    }

    public DateTime getBorrowedDateTime() {
        return borrowedDateTime;
    }

    public void setBorrowedDateTime(DateTime borrowedDateTime) {
        this.borrowedDateTime = borrowedDateTime;
    }

    @Override
    public String toString() {
        return "LibraryItem{" +
                "isbn=" + isbn +
                ", title='" + title + '\'' +
                ", sector='" + sector + '\'' +
                ", publicationDate='" + publicationDate + '\'' +
                ", currentReader=" + currentReader +
                ", borrowedDateTime=" + borrowedDateTime +
                '}';
    }
}
