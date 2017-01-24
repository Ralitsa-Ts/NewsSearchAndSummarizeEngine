package com.news.summary.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "articlesBySearch")
public class ArticleBySearch {

    @Id
    private String id;

    private String articleId;

    private String searchId;

    public ArticleBySearch() {
    }

    public ArticleBySearch(String id, String articleId, String searchId) {
        this.id = id;
        this.articleId = articleId;
        this.searchId = searchId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getSearchId() {
        return searchId;
    }

    public void setSearchId(String searchId) {
        this.searchId = searchId;
    }
}
