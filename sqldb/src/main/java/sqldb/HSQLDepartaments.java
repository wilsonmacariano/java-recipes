package sqldb;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HSQLDepartaments extends DatabaseCrud<Departament> {

    public HSQLDepartaments(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public Departament add(Departament departament) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement statement = conn.prepareStatement(
                     "INSERT INTO DEPARTAMENT (NAME) VALUES (?)",
                     Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, departament.getName());
            statement.executeUpdate();
            conn.commit();
            return new Departament(generatedId(statement),
                    departament.getName());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<Departament> all() {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement statement = conn.prepareStatement(
                     "SELECT ID, NAME FROM DEPARTAMENT")) {
            ResultSet result = statement.executeQuery();
            final List<Departament> departaments = new ArrayList<>();
            while (result.next())
                departaments.add(new Departament(result.getInt("ID"),
                        result.getString("NAME")));
            return departaments;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
