package com.news.summary.client.repository;

import java.util.List;

import com.news.summary.models.Search;

public interface SearchesClient {

    void save(Search search);

    void update(Search search);

    List<Search> getSearchesByStatus(String status);

    Search getCurrentSearch();
}
