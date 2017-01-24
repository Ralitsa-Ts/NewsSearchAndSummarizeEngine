package com.news.summary.repositories;

import org.springframework.data.repository.CrudRepository;

import com.news.summary.models.ArticleBySearch;

public interface ArticlesBySearchRepository
                extends CrudRepository<ArticleBySearch, String> {
}
