package com.news.summary.client.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.news.summary.client.repository.ArticlesClient;
import com.news.summary.models.Article;
import com.news.summary.repositories.ArticlesRepository;

@Component
public class ArticlesClientImpl implements ArticlesClient {

    @Autowired
    private ArticlesRepository articlesRepository;

    @Override
    public synchronized void save(Article article) {
        articlesRepository.save(article);
    }

    @Override
    public synchronized void update(Article article) {
        articlesRepository.delete(article.getId());
        articlesRepository.save(article);
    }

}
