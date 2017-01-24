package com.news.summary.client.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.helper.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.uuid.Generators;
import com.news.summary.client.repository.ArticlesClient;
import com.news.summary.models.Article;
import com.news.summary.models.ArticleBySearch;
import com.news.summary.repositories.ArticlesBySearchRepository;
import com.news.summary.repositories.ArticlesRepository;

@Component
public class ArticlesClientImpl implements ArticlesClient {

    @Autowired
    private ArticlesRepository articlesRepository;

    @Autowired
    private ArticlesBySearchRepository articlesBySearchRepository;

    @Override
    public synchronized void saveArticleBySearchId(Article article, String searchId) {
        Validate.notEmpty(searchId);
        Validate.notNull(article);
        articlesRepository.save(article);
        articlesBySearchRepository.save(
                        new ArticleBySearch(Generators.timeBasedGenerator().generate().toString(),
                                        article.getId(), searchId));
    }

    @Override
    public synchronized List<Article> getArticlesBySearchId(String searchId) {
        Validate.notEmpty(searchId);
        Iterable<ArticleBySearch> articlesBySearch = articlesBySearchRepository.findAll();
        List<Article> articlesBySearchId = new ArrayList<>();
        for (ArticleBySearch articleBySearch : articlesBySearch) {
            Article article = articlesRepository.findOne(articleBySearch.getArticleId());
            if (article != null) {
                articlesBySearchId.add(article);
            }
        }
        return articlesBySearchId;
    }

    @Override
    public List<Article> getArticlesByUrl(String url) {
        Validate.notEmpty(url);
        Iterable<Article> articles = articlesRepository.findAll();
        List<Article> articlesByUrl = new ArrayList<>();
        for (Article article : articles) {
            if (article.getUrl().equals(url)) {
                articlesByUrl.add(article);
            }
        }
        return articlesByUrl;
    }
}
