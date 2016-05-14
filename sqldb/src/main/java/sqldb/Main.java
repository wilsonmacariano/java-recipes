package sqldb;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String... args) throws Exception {
        HikariConfig config = configureHakari("jdbc:hsqldb:file:tempDB");
        HikariDataSource dataSource = new HikariDataSource(config);
        dropTables(dataSource);
        createTables(dataSource);

        Crud<Person> persons = new HSQLPersons(dataSource);
        insertSomePersons(persons);
        queryAllPersons(persons);

        Crud<Departament> departaments = new HSQLDepartaments(dataSource);
        insertSomeDepartaments(departaments);
        queryAllDepartaments(departaments);
    }


    private static void queryAllPersons(Crud<Person> persons) {
        persons.all().forEach(p -> System.out.println(p.getName()));
    }

    private static void insertSomePersons(Crud<Person> persons) {
        List<Person> personsToBeRegistered = Arrays.asList(new Person("Wilson"),
                new Person("Heitor"), new Person("Eveline"));
        personsToBeRegistered.forEach(p -> persons.add(p));
    }

    private static void insertSomeDepartaments(Crud<Departament> departaments) {
        List<Departament> departamentsToBeAdded = Arrays.asList(new Departament("Security"),
                new Departament("IT"));
        departamentsToBeAdded.forEach(d -> departaments.add(d));
    }

    private static void queryAllDepartaments(Crud<Departament> departaments) {
        departaments.all().forEach(p -> System.out.println(p.getName()));
    }

    private static void dropTables(DataSource dataSource) throws SQLException {
        try {
            runSql(dataSource, "DROP TABLE PERSON");
        } catch (Exception ignore) {

        }
        try {
            runSql(dataSource, "DROP TABLE DEPARTAMENT");
        } catch (Exception ignore) {

        }
    }

    private static HikariConfig configureHakari(String jdbcUrl) {
        HikariConfig config = new HikariConfig();
        config.setAutoCommit(false);
        config.setJdbcUrl(jdbcUrl);
        config.setUsername("");
        config.setPassword("");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        return config;
    }

    private static void createTables(DataSource ds) throws SQLException {
        runSql(ds, "CREATE TABLE PERSON ( ID INTEGER GENERATED BY DEFAULT " +
                "AS IDENTITY(START WITH 1, INCREMENT BY 1) PRIMARY KEY," +
                " NAME VARCHAR(120) )");
        runSql(ds, "CREATE TABLE DEPARTAMENT ( ID INTEGER GENERATED BY DEFAULT " +
                "AS IDENTITY(START WITH 1, INCREMENT BY 1) PRIMARY KEY," +
                " NAME VARCHAR(120) )");
    }

    private static void runSql(DataSource ds, String sql) throws SQLException {
        try (Connection connection = ds.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     sql)) {
            statement.execute();
            connection.commit();
        }
    }

}