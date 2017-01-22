package com.news.summary.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.news.summary.client.repository.SearchesClient;
import com.news.summary.models.Search;
import com.news.summary.models.SearchStatus;

@Controller
public class ArticleController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleController.class);

    @Autowired
    private ArticleControllerHelper articleManager;

    @Autowired
    private SearchesClient searchesClient;

    @RequestMapping("/find-summarized-article")
    @ResponseBody
    public String findSummarizedArticle(String searchText) {
        LOGGER.info("Received request for finding a summarized article. Search is {}.", searchText);
        Search search = null;
        try {
            if (!searchesClient.getSearchesByStatus(SearchStatus.IN_PROGRESS.toString())
                            .isEmpty()) {
                return "There is already a search in progress. Thy again when it finishes";
            }
            search = articleManager.createSearch(searchText);
            LOGGER.info("Initiated new search for '{}'.", searchText);
            String summarizedText = articleManager.findSummarizedArticle(search);
            LOGGER.info("Retrieval of summarized article successfully completed.");
            search.setStatus(SearchStatus.COMPLETED.toString());
            searchesClient.update(search);
            return summarizedText;
        } catch (Exception ex) {
            if (search != null) {
                search.setStatus(SearchStatus.ERROR.toString());
                searchesClient.update(search);
            }
            return "Could not retrieve summarized article." + ex.toString();
        }
    }
}