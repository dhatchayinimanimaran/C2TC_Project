package com_tnsif_onlineshopping.services;

import com_tnsif_onlineshopping.entities.*;
import java.util.HashMap;
import java.util.Map;

public class CustomerService {
    private static final CustomerService INSTANCE = new CustomerService();
    private final Map<Integer, Customer> customers = new HashMap<>();

    private CustomerService() {}

    public static CustomerService getInstance() { return INSTANCE; }

    public boolean createCustomer(Customer c) {
        if (customers.containsKey(c.getUserId())) return false;
        customers.put(c.getUserId(), c);
        return true;
    }

    public void viewCustomers() {
        if (customers.isEmpty()) {
            System.out.println("No customers present.");
            return;
        }
        System.out.println("Customers:");
        for (Customer c : customers.values()) {
            System.out.println(c);
        }
    }

    public Customer getCustomerById(int id) { return customers.get(id); }
}
