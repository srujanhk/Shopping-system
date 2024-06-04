package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Optional;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
public class UserDAOTest {
    private UserDAO userDAO;
    private DatabaseConnection databaseConnection;

    @BeforeEach
    public void setUp() {
        databaseConnection = mock(DatabaseConnection.class);
        userDAO = new MySQLUserDAO(databaseConnection);
    }

    @Test
    public void testRegisterUser() {
        User user = new User(1, "epaulet", "paul", "test@example.com");

        // Mocking database connection
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/testes", "epaulet", "paul");
            when(databaseConnection.getConnection()).thenReturn(connection);

            assertDoesNotThrow(() -> userDAO.registerUser(user));

            // Verify that the method to get connection was called
            verify(databaseConnection).getConnection();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testLoginUser() {
        String username = "epaulet";
        String password = "paul";
        User expectedUser = new User(1, username, password, "test@example.com");

        // Mocking database connection and result set
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/testes", "username", "password");
            PreparedStatement statement = mock(PreparedStatement.class);
            ResultSet resultSet = mock(ResultSet.class);

            when(connection.prepareStatement(anyString())).thenReturn(statement);
            when(statement.executeQuery()).thenReturn(resultSet);

            when(resultSet.next()).thenReturn(true);
            when(resultSet.getInt("id")).thenReturn(expectedUser.getId());
            when(resultSet.getString("username")).thenReturn(expectedUser.getUsername());
            when(resultSet.getString("password")).thenReturn(expectedUser.getPassword());
            when(resultSet.getString("email")).thenReturn(expectedUser.getEmail());

            when(databaseConnection.getConnection()).thenReturn(connection);

            Optional<User> actualUser = userDAO.loginUser(username, password);
//            assertTrue(actualUser.isPesent());
            assertTrue(actualUser.get().getUsername().equals(expectedUser.getUsername()));

            // Verify that the method to get connection was called
            verify(databaseConnection).getConnection();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
}
