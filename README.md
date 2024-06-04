# Simplified Shopping System

## Project Overview

This project is part of SE 540, focusing on applying fundamental principles and design patterns learned throughout the course. It aims to provide hands-on experience in building a robust and maintainable software application that incorporates SOLID principles, Singleton, Factory, and Builder design patterns. The system allows customers to browse products, add items to their cart, place orders, and simulate the payment process.

## Features

- **User Authentication:** A login system where cart data is associated with customer accounts.
- **Product Catalog:** A catalog loaded from a file, database, or in-memory with essential product information.
- **Order Processing:** Utilizes Singleton and Factory patterns for cart management and product creation.
- **Payment Processing:** A simulated payment system (mock payment gateway) separate from core shopping functionalities.
- **Logging:** Records important events and transactions.

## SOLID Principles

- **Single Responsibility Principle (SRP):** Each class has a single responsibility, with separate classes for different functionalities.
- **Open/Closed Principle (OCP):** System designed for extensibility without modification of existing code.
- **Liskov Substitution Principle (LSP):** Subclasses can replace their base classes without affecting program correctness.
- **Interface Segregation Principle (ISP):** Interfaces are specific to client needs, avoiding irrelevant methods.
- **Dependency Inversion Principle (DIP):** Uses dependency injection for decoupling modules.

## Design Patterns

- **Singleton:** Ensures a single instance of the shopping cart throughout the application.
- **Factory:** `ProductFactory` class encapsulates the creation of product objects.
- **Builder:** `CartBuilder` provides a fluent interface for constructing shopping carts.

## Testing

Develop unit tests for critical components such as the shopping cart, product catalog, and order processing.

## Getting Started

### Prerequisites

- Java 11 or later
- Maven or similar build tool

### Running the Application

1. Clone the repository:
   ```bash
   git clone https://github.com/srujanhk/Shopping-system/tree/master
   cd jdbcconnection

2. Build the project
   ```bash
   mvn clean install

3. Run the project
   ```bash
   java -jar 
