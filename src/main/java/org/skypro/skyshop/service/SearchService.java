package org.skypro.skyshop.service;

import org.skypro.skyshop.model.search.SearchResult;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class SearchService {

    private final StorageService storageService;

    public SearchService(StorageService storageService) {
        this.storageService = storageService;
    }

    public Collection<SearchResult> search(String pattern) {
        if (pattern == null || pattern.isBlank()) {
            return java.util.Collections.emptyList();
        }

        String lowerCasePattern = pattern.toLowerCase();

        return storageService.getAllSearchables().stream()
                .filter(searchable -> searchable.getSearchTerm()
                        .toLowerCase()
                        .contains(lowerCasePattern))
                .map(SearchResult::fromSearchable)
                .collect(Collectors.toList());
    }
}
