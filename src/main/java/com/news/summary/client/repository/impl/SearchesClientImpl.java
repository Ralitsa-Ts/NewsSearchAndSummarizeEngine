package com.news.summary.client.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.news.summary.client.repository.SearchesClient;
import com.news.summary.models.Search;
import com.news.summary.models.SearchStatus;
import com.news.summary.repositories.SearchesRepository;

@Component
public class SearchesClientImpl implements SearchesClient {

    @Autowired
    private SearchesRepository searchesRepository;

    @Override
    public synchronized void save(Search search) {
        searchesRepository.save(search);
    }

    @Override
    public synchronized void update(Search search) {
        searchesRepository.delete(search.getId());
        searchesRepository.save(search);
    }

    @Override
    public synchronized List<Search> getSearchesByStatus(String status) {
        Iterable<Search> searches = searchesRepository.findAll();
        List<Search> searchesByStatus = new ArrayList<>();
        for (Search search : searches) {
            if (search.getStatus().equals(status)) {
                searchesByStatus.add(search);
            }
        }
        return searchesByStatus;
    }

    @Override
    public synchronized Search getCurrentSearch() {
        List<Search> searches = getSearchesByStatus(SearchStatus.IN_PROGRESS.toString());
        if (!searches.isEmpty()) {
            /**
             * We are assuming that only one search in progress is allowed.
             */
            return searches.get(0);
        }
        return null;
    }
}
