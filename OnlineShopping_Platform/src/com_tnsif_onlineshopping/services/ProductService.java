package com_tnsif_onlineshopping.services;

import com_tnsif_onlineshopping.entities.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ProductService {
    private static final ProductService INSTANCE = new ProductService();
    private final Map<Integer, Product> products = new HashMap<>();

    private ProductService() {}

    public static ProductService getInstance() { return INSTANCE; }

    public boolean addProduct(Product p) {
        if (products.containsKey(p.getProductId())) return false;
        products.put(p.getProductId(), p);
        return true;
    }

    public boolean removeProduct(int productId) {
        return products.remove(productId) != null;
    }

    public Product getProductById(int id) {
        return products.get(id);
    }

    public Collection<Product> getAllProducts() { return products.values(); }

    public void viewProducts() {
        if (products.isEmpty()) {
            System.out.println("No products available.");
            return;
        }
        System.out.println("Products:");
        for (Product p : products.values()) {
            System.out.println(p);
        }
    }
}
