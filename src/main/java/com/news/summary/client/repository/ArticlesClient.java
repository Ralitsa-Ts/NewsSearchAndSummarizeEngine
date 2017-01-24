package com.news.summary.client.repository;

import java.util.List;

import com.news.summary.models.Article;

public interface ArticlesClient {

    void saveArticleBySearchId(Article article, String searchId);

    List<Article> getArticlesBySearchId(String searchId);

    List<Article> getArticlesByUrl(String url);
}
