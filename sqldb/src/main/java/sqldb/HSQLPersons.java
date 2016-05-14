package sqldb;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HSQLPersons extends DatabaseCrud<Person> {

    public HSQLPersons(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public Person add(Person person) {
        String sql = "INSERT INTO PERSON (NAME) VALUES (?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql,
                     Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, person.getName());
            statement.executeUpdate();
            conn.commit();
            return new Person(generatedId(statement),
                    person.getName());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Person> all() {
        String sql = "SELECT ID, NAME FROM PERSON";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            ResultSet result = statement.executeQuery();
            final List<Person> persons = new ArrayList<>();
            while (result.next())
                persons.add(new Person(result.getInt("ID"),
                        result.getString("NAME")));
            return persons;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
