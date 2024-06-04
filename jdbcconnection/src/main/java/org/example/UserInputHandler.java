package org.example;
import java.util.Scanner;

public interface UserInputHandler {
    User getUserRegistrationInput();
    LoginInput getLoginInput();
    Product getProductInput();
}

class LoginInput {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    // Getters and Setters
}


class ConsoleUserInputHandler implements UserInputHandler {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public User getUserRegistrationInput() {
        User user = new User();
        System.out.print("Enter username: ");
        user.setUsername(scanner.nextLine());
        System.out.print("Enter password: ");
        user.setPassword(scanner.nextLine());
        System.out.print("Enter email: ");
        user.setEmail(scanner.nextLine());
        return user;
    }

    @Override
    public LoginInput getLoginInput() {
        LoginInput loginInput = new LoginInput();
        System.out.print("Enter username: ");
        loginInput.setUsername(scanner.nextLine());
        System.out.print("Enter password: ");
        loginInput.setPassword(scanner.nextLine());
        return loginInput;
    }

    @Override
    public Product getProductInput() {
        Product product = new Product();
        System.out.print("Enter product name: ");
        product.setName(scanner.nextLine());
        System.out.print("Enter product category: ");
        product.setCategory(scanner.nextLine());
        System.out.print("Enter product price: ");
        product.setPrice(Double.parseDouble(scanner.nextLine()));
        return product;
    }
}

