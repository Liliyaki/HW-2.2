package org.skypro.skyshop.model.search;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public final class SearchResult {
    private final UUID id;
    private final String name;
    private final String contentType;

    @JsonCreator
    private SearchResult(@JsonProperty("id") UUID id,
                         @JsonProperty("name") String name,
                         @JsonProperty("contentType") String contentType) {
        this.id = id;
        this.name = name;
        this.contentType = contentType;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getContentType() {
        return contentType;
    }

    public static SearchResult fromSearchable(Searchable searchable) {
        return new SearchResult(searchable.getId(),
                searchable.getProductName(),
                searchable.getContentType());
    }
}
