package models;

public class Reader {
    private int readerID;
    private String readerName;
    private String readerMobileno;
    private String readerEmail;

    public Reader(int readerID, String readerName, String readerMobileno, String readerEmail) {
        this.readerID = readerID;
        this.readerName = readerName;
        this.readerMobileno = readerMobileno;
        this.readerEmail = readerEmail;
    }

    public int getReaderID() {
        return readerID;
    }

    public void setReaderID(int readerID) {
        this.readerID = readerID;
    }

    public String getReaderName() {
        return readerName;
    }

    public void setReaderName(String readerName) {
        this.readerName = readerName;
    }

    public String getReaderMobileno() {
        return readerMobileno;
    }

    public void setReaderMobileno(String readerMobileno) {
        this.readerMobileno = readerMobileno;
    }

    public String getReaderEmail() {
        return readerEmail;
    }

    public void setReaderEmail(String readerEmail) {
        this.readerEmail = readerEmail;
    }
}
