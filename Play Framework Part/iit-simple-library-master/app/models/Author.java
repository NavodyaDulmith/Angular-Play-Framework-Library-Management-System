package models;

public class Author extends Person {

    public Author(String name) {
        super(name);
    }

    @Override
    public String toString() {
        return "Author{" +
                "name='" + name + '\'' +
                '}';
    }
}
