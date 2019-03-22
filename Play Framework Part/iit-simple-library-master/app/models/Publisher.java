package models;

public class Publisher extends Person {

    public Publisher(String name) {
        super(name);
    }

    @Override
    public String toString() {
        return "Publisher{" +
                "name='" + name + '\'' +
                '}';
    }
}
