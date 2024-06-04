package org.example;

import java.util.HashMap;
import java.util.Map;

public class CartService {

    private final Map<Integer, CartItem> cartItems = new HashMap<>();

    public void addItemToCart(Product product, int quantity) {
        if (cartItems.containsKey(product.getId())) {
            CartItem existingItem = cartItems.get(product.getId());
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        } else {
            CartItem newItem = new CartItem();
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            cartItems.put(product.getId(), newItem);
        }
    }

    public void removeItemFromCart(int productId) {
        cartItems.remove(productId);
    }

    public Map<Integer, CartItem> getCartItems() {
        return cartItems;
    }
    public void clearCart() {
        cartItems.clear();
    }
    public double calculateTotal() {
        return cartItems.values().stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
    }
}
