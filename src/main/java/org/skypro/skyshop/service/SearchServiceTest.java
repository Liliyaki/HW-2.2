package org.skypro.skyshop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.model.search.SearchResult;
import org.skypro.skyshop.model.search.Searchable;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class SearchServiceTest {

    @Mock
    private StorageService storageServiceMock;

    @InjectMocks
    private SearchService searchService;

    private UUID testId;
    private Product testProduct;

    @BeforeEach
    void setUp() {

        testId = UUID.randomUUID();

        testProduct = new SimpleProduct(testId, "TestProduct", 100);
    }

    @Test
    void searchShouldReturnEmptyListWhenStorageIsEmpty() {

        when(storageServiceMock.getAllSearchables()).thenReturn(List.of());


        Collection<SearchResult> results = searchService.search("anything");


        assertThat(results)
                .as("Поиск в пустом хранилище должен возвращать пустой список")
                .isEmpty();
    }

    @Test
    void searchShouldReturnEmptyListWhenNoMatches() {

        Product appleProduct = new SimpleProduct(UUID.randomUUID(), "Apple", 50);


        when(storageServiceMock.getAllSearchables()).thenReturn(List.of(appleProduct));


        Collection<SearchResult> results = searchService.search("Orange");


        assertThat(results)
                .as("Поиск с неподходящим паттерном должен возвращать пустой список")
                .isEmpty();
    }

    @Test
    void searchShouldReturnResultsWhenMatchFound() {

        when(storageServiceMock.getAllSearchables()).thenReturn(List.of(testProduct));


        Collection<SearchResult> results = searchService.search("Test");


        assertThat(results)
                .as("Поиск по подходящему паттерну должен вернуть результаты")
                .hasSize(1);


        SearchResult firstResult = results.iterator().next();


        assertThat(firstResult.getId())
                .as("ID результата должен совпадать с ID продукта")
                .isEqualTo(testId);

        assertThat(firstResult.getName())
                .as("Имя результата должно совпадать с именем продукта")
                .isEqualTo("TestProduct");

        assertThat(firstResult.getContentType())
                .as("Тип контента должен быть PRODUCT")
                .isEqualTo("PRODUCT");
    }


    @Test
    void searchShouldBeCaseInsensitive() {


        when(storageServiceMock.getAllSearchables()).thenReturn(List.of(testProduct));


        Collection<SearchResult> results = searchService.search("test");

        assertThat(results)
                .as("Поиск должен быть регистронезависимым")
                .hasSize(1);
    }


    @Test
    void searchShouldReturnEmptyListForEmptyPattern() {
        when(storageServiceMock.getAllSearchables()).thenReturn(List.of(testProduct));

        Collection<SearchResult> resultsForEmpty = searchService.search("");
        Collection<SearchResult> resultsForNull = searchService.search(null);

        assertThat(resultsForEmpty)
                .as("Пустой паттерн должен возвращать пустой список")
                .isEmpty();

        assertThat(resultsForNull)
                .as("Null паттерн должен возвращать пустой список")
                .isEmpty();
    }
}


