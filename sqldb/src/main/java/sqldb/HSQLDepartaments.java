package sqldb;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HSQLDepartaments extends DatabaseCrud<Department> {

    public HSQLDepartaments(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public Department add(Department department) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement statement = conn.prepareStatement(
                     "INSERT INTO DEPARTAMENT (NAME) VALUES (?)",
                     Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, department.getName());
            statement.executeUpdate();
            conn.commit();
            return new Department(generatedId(statement),
                    department.getName());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<Department> all() {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement statement = conn.prepareStatement(
                     "SELECT ID, NAME FROM DEPARTAMENT")) {
            ResultSet result = statement.executeQuery();
            final List<Department> departments = new ArrayList<>();
            while (result.next())
                departments.add(new Department(result.getInt("ID"),
                        result.getString("NAME")));
            return departments;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
