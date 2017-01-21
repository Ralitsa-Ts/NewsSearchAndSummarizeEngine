package com.news.summary.repositories;

import org.springframework.data.repository.CrudRepository;

import com.news.summary.models.Article;

public interface ArticlesRepository extends CrudRepository<Article, Long> {
}