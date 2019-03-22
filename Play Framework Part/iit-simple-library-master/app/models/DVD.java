package models;

public class DVD extends LibraryItem {

    private String avilableSubtitles;
    private String availableLanguages;
    private Producer producer;
    private String actors;

    public DVD(long isbn, String title, String sector, String publicationDate, Reader currentReader, DateTime borrowedDateTime, String avilableSubtitles, String availableLanguages, Producer producer, String actors) {
        super(isbn, title, sector, publicationDate, currentReader, borrowedDateTime);
        this.avilableSubtitles = avilableSubtitles;
        this.availableLanguages = availableLanguages;
        this.producer = producer;
        this.actors = actors;
    }

    public String getAvilableSubtitles() {
        return avilableSubtitles;
    }

    public void setAvilableSubtitles(String avilableSubtitles) {
        this.avilableSubtitles = avilableSubtitles;
    }

    public String getAvailableLanguages() {
        return availableLanguages;
    }

    public void setAvailableLanguages(String availableLanguages) {
        this.availableLanguages = availableLanguages;
    }

    public Producer getProducer() {
        return producer;
    }

    public void setProducer(Producer producer) {
        this.producer = producer;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    @Override
    public String toString() {
        return "DVD{" +
                "  avilableSubtitles='" + avilableSubtitles + '\'' +
                ", availableLanguages='" + availableLanguages + '\'' +
                ", producer=" + producer +
                ", actors='" + actors + '\'' +
                '}';
    }
}
