package sqldb;

public class Department {

    private Integer id;
    private String name;

    public Department(Integer id, String name) {
        this(name);
        this.id = id;
    }

    public Department(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

}
