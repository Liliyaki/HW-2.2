package org.skypro.skyshop.model.basket;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.*;

@Component
@SessionScope
public class ProductBasket {
    private final Map <UUID,Integer> products = new HashMap<>();
    public void add (UUID id) {
        products.merge(id, 1, Integer::sum);
    }
    public Map <UUID, Integer> getProducts(){
        return Collections.unmodifiableMap(products);
    }
}
