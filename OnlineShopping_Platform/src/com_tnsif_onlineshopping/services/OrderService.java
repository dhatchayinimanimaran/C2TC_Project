package com_tnsif_onlineshopping.services;

import com_tnsif_onlineshopping.entities.*;
import java.util.HashMap;
import java.util.Map;

public class OrderService {
    private static final OrderService INSTANCE = new OrderService();
    private final Map<Integer, Order> orders = new HashMap<>();

    private OrderService() {}

    public static OrderService getInstance() { return INSTANCE; }

    public boolean placeOrder(Customer customer) {
        ShoppingCart cart = customer.getShoppingCart();
        if (cart == null || cart.isEmpty()) return false;

        ProductService productService = ProductService.getInstance();
        Order order = new Order(customer);

        // Validate & reserve stock
        for (Map.Entry<Integer, Integer> e : cart.getItems().entrySet()) {
            Product p = productService.getProductById(e.getKey());
            int qty = e.getValue();
            if (p == null || p.getStockQuantity() < qty) {
                System.out.println("Error: product " + e.getKey() + " not available in requested quantity.");
                return false;
            }
            // reduce stock
            p.setStockQuantity(p.getStockQuantity() - qty);
            order.getProducts().add(new ProductQuantityPair(p, qty));
        }

        // persist order
        orders.put(order.getOrderId(), order);
        customer.getOrders().add(order);
        cart.clear();
        return true;
    }

    public void viewAllOrders() {
        if (orders.isEmpty()) {
            System.out.println("No orders placed yet.");
            return;
        }
        System.out.println("Orders:");
        for (Order o : orders.values()) printOrder(o);
    }

    public void viewOrdersForCustomer(int customerId) {
        boolean found = false;
        for (Order o : orders.values()) {
            if (o.getCustomer().getUserId() == customerId) {
                if (!found) System.out.println("Orders:");
                printOrder(o);
                found = true;
            }
        }
        if (!found) System.out.println("No orders for the given customer.");
    }

    public boolean updateOrderStatus(int orderId, String newStatus) {
        Order o = orders.get(orderId);
        if (o == null) return false;
        String normalized = normalizeStatus(newStatus);
        if (normalized == null) return false;
        o.setStatus(normalized);
        return true;
    }

    private String normalizeStatus(String s) {
        if (s == null) return null;
        s = s.trim().toLowerCase();
        return switch (s) {
            case "pending" -> "Pending";
            case "completed" -> "Completed";
            case "delivered" -> "Delivered";
            case "cancelled", "canceled" -> "Cancelled";
            default -> null;
        };
    }

    private void printOrder(Order o) {
        System.out.println("Order ID: " + o.getOrderId() +
                           ", Customer: " + o.getCustomer().getUsername() +
                           ", Status: " + o.getStatus() +
                           ", Total: " + o.getTotal());
        for (ProductQuantityPair pqp : o.getProducts()) {
            System.out.println("  Product: " + pqp.getProduct().getName() +
                               ", Quantity: " + pqp.getQuantity());
        }
    }
}
