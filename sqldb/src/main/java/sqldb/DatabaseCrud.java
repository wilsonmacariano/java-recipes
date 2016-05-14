package sqldb;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class DatabaseCrud<T> implements Crud<T> {

    protected DataSource dataSource;

    public DatabaseCrud(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    protected int generatedId(PreparedStatement statement) throws SQLException {
        ResultSet results = statement.getGeneratedKeys();
        results.next();
        return results.getInt(1);
    }
}
