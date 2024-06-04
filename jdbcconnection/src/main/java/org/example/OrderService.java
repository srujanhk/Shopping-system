package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderService {

    private final DatabaseConnection databaseConnection;

    public OrderService(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public void placeOrder(int userId, Map<Integer, CartItem> cartItems) {
        double totalAmount = cartItems.values().stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();

        String orderQuery = "INSERT INTO order_details (user_id, total_amount) VALUES (?, ?)";
        String orderItemQuery = "INSERT INTO order_items (order_id, product_id, quantity) VALUES (?, ?, ?)";

        try (Connection connection = databaseConnection.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement orderStatement = connection.prepareStatement(orderQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
                orderStatement.setInt(1, userId);
                orderStatement.setDouble(2, totalAmount);
                orderStatement.executeUpdate();

                try (ResultSet generatedKeys = orderStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int orderId = generatedKeys.getInt(1);

                        try (PreparedStatement orderItemStatement = connection.prepareStatement(orderItemQuery)) {
                            for (CartItem item : cartItems.values()) {
                                orderItemStatement.setInt(1, orderId);
                                orderItemStatement.setInt(2, item.getProduct().getId());
                                orderItemStatement.setInt(3, item.getQuantity());
                                orderItemStatement.addBatch();
                            }
                            orderItemStatement.executeBatch();
                        }
                    }
                }

                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Order> getOrdersByUserId(int userId) {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM order_details WHERE user_id = ?";

        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Order order = new Order();
                    order.setId(resultSet.getInt("id"));
                    order.setUserId(userId);
                    order.setTotalAmount(resultSet.getDouble("total_amount"));
                    order.setOrderDate(resultSet.getTimestamp("order_date").toLocalDateTime());
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }
}

