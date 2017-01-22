package com.news.summary.controllers;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.uuid.Generators;
import com.news.summary.client.repository.SearchesClient;
import com.news.summary.models.Search;
import com.news.summary.models.SearchStatus;
import com.news.summary.services.crawler.articles.Crawler;
import com.news.summary.utils.KeywordsExtractor;

import uk.org.lidalia.slf4jext.LoggerFactory;

@Component
public class ArticleControllerHelper {

    private static final String SPACE_DELIMETER = " ";
    private static final String BBC_NEWS_URL = "http://www.bbc.com/news";
    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleControllerHelper.class);

    @Autowired
    private Crawler crawler;

    @Autowired
    private SearchesClient searchesClient;

    public String findSummarizedArticle(Search search) {
        try {
            LOGGER.info("Searching for summarized text for search with id {}.", search.getId());
            crawler.crawl(Arrays.asList(BBC_NEWS_URL));
        } catch (Exception e) {
            String errorMessage = "Failed to find summarized article for seach with id {}."
                            + search.getId();
            LOGGER.error(errorMessage, e);
            throw new RuntimeException(errorMessage, e);
        }
        return "";
    }

    public Search createSearch(String searchText) {
        List<String> keywords = KeywordsExtractor.getKeywordStringsList(searchText);
        Search search = new Search(Generators.timeBasedGenerator().generate().toString(),
                        StringUtils.join(keywords, SPACE_DELIMETER),
                        SearchStatus.IN_PROGRESS.toString());
        searchesClient.save(search);
        return search;
    }
}
