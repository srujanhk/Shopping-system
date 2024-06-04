package org.example;

public class ServiceFactory {

    public static UserDAO createUserDAO() {
        DatabaseConnection dbConnection = new MySQLDatabaseConnection(); // gives access to connection
        return new MySQLUserDAO(dbConnection); //gives object for Registering and login
    }

    public static ProductDAO createProductDAO() {
        DatabaseConnection dbConnection = new MySQLDatabaseConnection();
        return new MySQLProductDAO(dbConnection);
    }

    public static CartService createCartService() {
        return new CartService();
    }

    public static OrderService createOrderService() {
        DatabaseConnection dbConnection = new MySQLDatabaseConnection();
        return new OrderService(dbConnection);
    }
}

