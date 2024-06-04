package org.example;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        UserDAO userDAO = ServiceFactory.createUserDAO();
        ProductDAO productDAO = ServiceFactory.createProductDAO();
        CartService cartService = ServiceFactory.createCartService();
        OrderService orderService = ServiceFactory.createOrderService();
        UserInputHandler userInputHandler = new ConsoleUserInputHandler();

        Scanner scanner = new Scanner(System.in);
        boolean running = true;


        System.out.println("Welcome to Online Shopping");
        while (running) {

            System.out.println("Choose an option:");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    // User registration
                    System.out.println("Register new user:");
                    User user = userInputHandler.getUserRegistrationInput();
                    userDAO.registerUser(user);
                    break;

                case 2:
                    // User login
                    System.out.println("Login:");
                    LoginInput loginInput = userInputHandler.getLoginInput();
                    Optional<User> optionalUser = userDAO.loginUser(loginInput.getUsername(), loginInput.getPassword());

                    if (optionalUser.isPresent()) {
                        User loggedInUser = optionalUser.get();
                        System.out.println("User logged in: " + loggedInUser.getUsername());
                        boolean loggedIn = true;

                        while (loggedIn) {
                            System.out.println("Choose an option:");
                            System.out.println("1. Add item to cart");
                            System.out.println("2. Delete item from cart");
                            System.out.println("3. View items in cart");
                            System.out.println("4. Place order");
                            System.out.println("5. View previous orders");
                            System.out.println("6. Logout");

                            int userChoice = scanner.nextInt();
                            scanner.nextLine();  // Consume newline

                            switch (userChoice) {
                                case 1:
                                    // Add item to cart
                                    System.out.println("Available products:");
                                    productDAO.getAllProducts().forEach(product -> {
                                        System.out.println("ID: " + product.getId() + ", Name: " + product.getName() +
                                                ", Price: $" + product.getPrice() + ", Category: " + product.getCategory());
                                    });
                                    System.out.println("Enter product ID to add to cart:");
                                    int productIdToAdd = scanner.nextInt();
                                    Optional<Product> productToAdd = productDAO.getProductById(productIdToAdd);
                                    if (productToAdd.isPresent()) {
                                        System.out.println("Enter quantity:");
                                        int quantity = scanner.nextInt();
                                        cartService.addItemToCart(productToAdd.get(), quantity);
                                    } else {
                                        System.out.println("Invalid product ID.");
                                    }
                                    break;

                                case 2:
                                    // Delete item from cart
                                    if (cartService.getCartItems().isEmpty()) {
                                        System.out.println("Cart is empty.");
                                    } else {
                                        cartService.getCartItems().forEach((id, item) -> {
                                            System.out.println("Product ID: " + id + ", Name: " + item.getProduct().getName() +
                                                    ", Quantity: " + item.getQuantity() + ", Price: $" + item.getProduct().getPrice());
                                        });
                                        System.out.println("Enter product ID to delete from cart:");
                                        int productIdToDelete = scanner.nextInt();
                                        Optional<Product> productToDelete = productDAO.getProductById(productIdToDelete);
                                        if (productToDelete.isPresent() && cartService.getCartItems().containsKey(productIdToDelete)) {
                                            cartService.removeItemFromCart(productIdToDelete);
                                            System.out.println("Item removed from cart.");
                                        } else {
                                            System.out.println("Item not found.");
                                        }
                                    }

                                    break;

                                case 3:
                                    // View items in cart
                                    if(!(cartService.getCartItems().isEmpty())) {
                                        System.out.println("Items in cart:");
                                        cartService.getCartItems().forEach((id, item) -> {
                                            System.out.println("Product ID: " + id + ", Name: " + item.getProduct().getName() +
                                                    ", Quantity: " + item.getQuantity() + ", Price: $" + item.getProduct().getPrice());
                                        });
                                    }else {
                                        System.out.println("Cart id Empty");
                                    }
                                    break;

                                case 4:
                                    // Place order
                                    orderService.placeOrder(loggedInUser.getId(), cartService.getCartItems());
                                    System.out.println("Order placed successfully.");
                                    System.out.println("Processing payment through dummy gateway...");
                                    System.out.println("Payment successful. Thank you for your purchase!");
                                    cartService.clearCart();
                                    break;

                                case 5:
                                    // View previous orders
                                    System.out.println("Previous orders:");
                                    orderService.getOrdersByUserId(loggedInUser.getId()).forEach(order -> {
                                        System.out.println("Order ID: " + order.getId() + ", Total Amount: $" + order.getTotalAmount() +
                                                ", Order Date: " + order.getOrderDate());
                                    });
                                    break;

                                case 6:
                                    // Logout
                                    loggedIn = false;
                                    System.out.println("User logged out.");
                                    break;

                                default:
                                    System.out.println("Invalid choice. Please try again.");
                                    break;
                            }
                        }
                    } else {
                        System.out.println("Invalid username or password. Please try again.");
                    }
                    break;

                case 3:
                    // Exit
                    running = false;
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }

        scanner.close();
    }
}
