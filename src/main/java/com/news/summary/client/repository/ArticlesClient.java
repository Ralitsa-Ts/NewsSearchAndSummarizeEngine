package com.news.summary.client.repository;

import com.news.summary.models.Article;

public interface ArticlesClient {

    void save(Article article);

    void update(Article article);
}
