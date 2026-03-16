package org.skypro.skyshop.model.product;

import org.skypro.skyshop.exception.NoSuchProductException;

import java.util.UUID;

public class DiscountedProduct extends Product {
    private final int basedPrice;
    private final int discount;

    public DiscountedProduct(UUID id, String productName, int basedPrice, int discount) {
        super(id, productName);
        if (basedPrice < 0 ){
            throw new NoSuchProductException("Базовая цена должна быть больше 0");
        }
        if (discount < 0 || discount > 100) {
            throw new IllegalArgumentException("Скидка должна быть в диапазоне от 0 до 100 включительно");
        }
        this.basedPrice = basedPrice;
        this.discount = discount;
    }

    @Override
    public int getProductPrice() {
        return basedPrice - (basedPrice * discount / 100);
    }
    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public String toString() {
        return productName + " со скидкой" + ": " + basedPrice + " рублей со скидкой " + discount + "%";
    }
}