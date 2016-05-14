package sqldb;

public class Person {

    private Integer id;
    private String name;

    public Person(Integer id, String name) {
        this(name);
        this.id = id;
    }

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }
}
