package sqldb;

public class Departament {

    private Integer id;
    private String name;

    public Departament(Integer id, String name) {
        this(name);
        this.id = id;
    }

    public Departament(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

}
