package com_tnsif_onlineshopping.entities;

package com_tnsif_onlineshopping.entities;
import java.util.ArrayList;
import java.util.List;

public class Customer extends User {
    private final String address;
    private final ShoppingCart shoppingCart = new ShoppingCart();
    private final List<Order> orders = new ArrayList<>();

    public Customer(int userId, String username, String email, String address) {
        super(userId, username, email);
        this.address = address;
    }

    public String getAddress() { return address; }
    public ShoppingCart getShoppingCart() { return shoppingCart; }
    public List<Order> getOrders() { return orders; }

    @Override
    public String toString() {
        return "Customer [userId=" + userId +
               ", username=" + username +
               ", email=" + email +
               ", address=" + address + "]";
    }
}