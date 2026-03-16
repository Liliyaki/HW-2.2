package org.skypro.skyshop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.skyshop.exception.NoSuchProductException;
import org.skypro.skyshop.model.basket.BasketItem;
import org.skypro.skyshop.model.basket.ProductBasket;
import org.skypro.skyshop.model.basket.UserBasket;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.product.SimpleProduct;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BasketServiceTest {

    @Mock
    private ProductBasket productBasketMock;

    @Mock
    private StorageService storageServiceMock;

    @InjectMocks
    private BasketService basketService;

    private UUID validId;
    private UUID invalidId;
    private Product testProduct;

    @BeforeEach
    void setUp() {
        validId = UUID.randomUUID();
        invalidId = UUID.randomUUID();
        testProduct = new SimpleProduct(validId, "TestProduct", 100);
    }

    @Test
    void addProductShouldThrowExceptionWhenProductNotFound() {

        when(storageServiceMock.getProductById(invalidId))
                .thenReturn(Optional.empty());
        assertThrows(NoSuchProductException.class,
                () -> basketService.addProductToBasket(invalidId),
                "Добавление несуществующего товара должно выбрасывать NoSuchProductException");

        verify(productBasketMock, never()).add(any());
    }

    @Test
    void addProductShouldCallBasketAddProductWhenProductExists() {
        when(storageServiceMock.getProductById(validId))
                .thenReturn(Optional.of(testProduct));
        basketService.addProductToBasket(validId);
        verify(productBasketMock, times(1))
                .add(validId);
    }

    @Test
    void getUserBasketShouldReturnEmptyBasketWhenBasketIsEmpty() {
        when(productBasketMock.getProducts())
                .thenReturn(Map.of());
        UserBasket userBasket = basketService.getUserBasket();
        assertThat(userBasket.getItems())
                .as("Корзина должна быть пустой")
                .isEmpty();

        assertThat(userBasket.getTotal())
                .as("Общая стоимость пустой корзины должна быть 0")
                .isZero();
    }

    @Test
    void getUserBasketShouldReturnCorrectBasketWithTotal() {
        UUID productId1 = UUID.randomUUID();
        UUID productId2 = UUID.randomUUID();
        Product product1 = new SimpleProduct(productId1, "Product 1", 100);
        Product product2 = new SimpleProduct(productId2, "Product 2", 200);
        Map<UUID, Integer> basketMap = Map.of(
                productId1, 2,
                productId2, 3
        );
        when(productBasketMock.getProducts()).thenReturn(basketMap);
        when(storageServiceMock.getProductById(productId1))
                .thenReturn(Optional.of(product1));
        when(storageServiceMock.getProductById(productId2))
                .thenReturn(Optional.of(product2));
        UserBasket userBasket = basketService.getUserBasket();
        assertThat(userBasket.getItems())
                .as("Корзина должна содержать 2 товара")
                .hasSize(2);
        assertThat(userBasket.getTotal())
                .as("Общая стоимость должна быть правильно посчитана")
                .isEqualTo(800);
        verify(storageServiceMock, times(1)).getProductById(productId1);
        verify(storageServiceMock, times(1)).getProductById(productId2);
    }

    @Test
    void getUserBasketShouldThrowExceptionWhenProductNotFoundInStorage() {

        UUID productId = UUID.randomUUID();
        Map<UUID, Integer> basketMap = Map.of(productId, 1);

        when(productBasketMock.getProducts()).thenReturn(basketMap);
        when(storageServiceMock.getProductById(productId))
                .thenReturn(Optional.empty());

        assertThrows(NoSuchProductException.class,
                () -> basketService.getUserBasket(),
                "Если продукт есть в корзине, но нет в хранилище - должно быть исключение");
    }

    @Test
    void getUserBasketReturnsUnmodifiableBasket() {
        when(productBasketMock.getProducts()).thenReturn(Map.of());

        UserBasket userBasket = basketService.getUserBasket();
        assertThrows(UnsupportedOperationException.class,
                () -> userBasket.getItems().add(null));
    }
}
