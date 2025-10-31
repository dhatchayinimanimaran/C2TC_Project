package com_tnsif_onlineshopping.entities;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
    // productId -> quantity
    private final Map<Integer, Integer> items = new HashMap<>();

    public void addItem(Product product, int quantity) {
        items.put(product.getProductId(),
                  items.getOrDefault(product.getProductId(), 0) + quantity);
    }

    public Map<Integer, Integer> getItems() {
        return Collections.unmodifiableMap(items);
    }

    public boolean isEmpty() { return items.isEmpty(); }
    public void clear() { items.clear(); }
}
