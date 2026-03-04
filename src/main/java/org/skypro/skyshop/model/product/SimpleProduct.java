package org.skypro.skyshop.model.product;

import java.util.UUID;

public class SimpleProduct extends Product {

    public final int price;

    public SimpleProduct(UUID id, String productName, int price) {
        super(id, productName);
        if (price < 0) {
            throw new IllegalArgumentException("Цена должна быть больше 0");
        }
        this.price = price;
    }

    @Override
    public int getProductPrice() {
        return price;
    }

    @Override
    public boolean isSpecial() {
        return false;
    }

    @Override
    public String toString() {
        return productName + ": " + price + " рублей.";
    }
}
