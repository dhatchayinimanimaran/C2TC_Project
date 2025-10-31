package com_tnsif_onlineshopping.entities;

package com_tnsif_onlineshopping.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Order {
    private static final AtomicInteger NEXT_ID = new AtomicInteger(1);

    private final int orderId;
    private final Customer customer;
    private final List<ProductQuantityPair> products = new ArrayList<>();
    private String status; // Pending, Completed, Delivered, Cancelled

    public Order(Customer customer) {
        this.orderId = NEXT_ID.getAndIncrement();
        this.customer = customer;
        this.status = "Pending";
    }

    public int getOrderId() { return orderId; }
    public Customer getCustomer() { return customer; }
    public List<ProductQuantityPair> getProducts() { return products; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public double getTotal() {
        double total = 0;
        for (ProductQuantityPair p : products) {
            total += p.getProduct().getPrice() * p.getQuantity();
        }
        return total;
    }
}
