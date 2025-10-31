package com_tnsif_onlineshopping.entities;

public class ProductQuantityPair {
    private final Product product;
    private final int quantity;

    public ProductQuantityPair(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }
}
