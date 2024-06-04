package org.example;

import java.util.List;
import java.util.Optional;

public interface ProductDAO {
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    void addProduct(Product product);

    Optional<Product> getProductById(int productIdToAdd);
}
