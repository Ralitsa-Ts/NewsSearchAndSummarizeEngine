package com.news.summary.repositories;

import org.springframework.data.repository.CrudRepository;

import com.news.summary.models.Search;

public interface SearchesRepository extends CrudRepository<Search, String> {
}
