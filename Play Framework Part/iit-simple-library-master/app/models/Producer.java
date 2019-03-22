package models;

public class Producer extends Person {

    public Producer(String name) {
        super(name);
    }

    @Override
    public String toString() {
        return "Producer{" +
                "name='" + name + '\'' +
                '}';
    }
}
