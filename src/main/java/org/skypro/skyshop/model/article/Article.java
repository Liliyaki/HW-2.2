package org.skypro.skyshop.model.article;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.skypro.skyshop.model.search.Searchable;

import java.util.Objects;
import java.util.UUID;

public class Article implements Searchable {
    private final String articleName;
    private final String text;
    private final UUID id;

    public Article(UUID id,String articleName, String text) {
        if (articleName == null || articleName.isEmpty()) {
            throw new IllegalArgumentException("Заголовок статьи не может быть null или пустой строкой");
        }
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("Содержание статьи не может быть null или пустой строкой");
        }
        this.id = id;
        this.articleName = articleName;
        this.text = text;
    }

    public UUID getId() {
        return id;
    }

    public String getArticleName() {
        return articleName;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return articleName + text;
    }

    @Override
    @JsonIgnore
    public String getSearchTerm() {
        return toString();
    }

    @Override
    @JsonIgnore
    public String getContentType() {
        return "ARTICLE";
    }

    @Override
    public String getProductName() {
        return articleName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return Objects.equals(articleName, article.articleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(articleName);
    }
}
