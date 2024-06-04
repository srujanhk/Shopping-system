package org.example;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public interface DatabaseConnection {
    Connection getConnection() throws SQLException;
}
class MySQLDatabaseConnection implements DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/ShoppingCartDB";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
