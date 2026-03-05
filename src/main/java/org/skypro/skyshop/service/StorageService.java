package org.skypro.skyshop.service;

import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.DiscountedProduct;
import org.skypro.skyshop.model.product.FixPriceProduct;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.model.search.Searchable;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.util.UUID.randomUUID;

@Service
public class StorageService {
    private final Map<UUID, Product> productStorage;
    private final Map<UUID, Article> articleStorage;

    public StorageService() {
        this.productStorage = new HashMap<>();
        this.articleStorage = new HashMap<>();
        testData();
    }
    public  Collection<Product> allProducts(){
        return productStorage.values();
    }
    public  Collection <Article> allArticles(){
        return articleStorage.values();
    }
    public Collection<Searchable> getAllSearchables() {
        List<Searchable> searchables = new ArrayList<>();
        searchables.addAll(productStorage.values());
        searchables.addAll(articleStorage.values());
        return searchables;
    }
    private void testData(){
        Product juice = new DiscountedProduct(randomUUID(),"Сок",250,70);
        Product chicken = new SimpleProduct(randomUUID(),"курица",500);
        Product potato = new DiscountedProduct(randomUUID(),"картошка",150,20);
        Product eggs = new DiscountedProduct(randomUUID(),"яйца", 150,10);
        Product lemonade = new FixPriceProduct(randomUUID(),"лимонад");
        Product tomato = new FixPriceProduct(randomUUID(),"помидоры");
        Article articleJuice = new Article(randomUUID(),"Магазинный сок: Польза или вред?", "В соке содержится много сахара, но также...");
        Article articleChicken = new Article(randomUUID(), "Курица как источник белка", "Не все части курицы имеют одинаковое КБЖУ...");
        Article articleTomato = new Article(randomUUID(), "Помидоры как источник клетчатки", "Помидоры действительно содержат в себе клетчатку, но...");
        productStorage.put(juice.getId(),juice);
        productStorage.put(chicken.getId(),chicken);
        productStorage.put(potato.getId(),potato);
        productStorage.put(eggs.getId(),eggs);
        productStorage.put(lemonade.getId(),lemonade);
        productStorage.put(tomato.getId(),tomato);
        articleStorage.put(articleJuice.getId(), articleJuice);
        articleStorage.put(articleChicken.getId(), articleChicken);
        articleStorage.put(articleTomato.getId(), articleTomato);
    }

}
