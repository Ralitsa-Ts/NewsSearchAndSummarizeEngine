package com.information.retrieval.controller.summarized.article;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.information.retrieval.indexer.Indexer;
import com.information.retrieval.summarization.Summarizer;

@Controller
public class ArticleController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleController.class);
    
    @Autowired
    private Indexer indexer;

    @RequestMapping("/find-summarized-article")
    @ResponseBody
    public String findSummarizedArticle(String searchText) {
        LOGGER.info("Received request for finding a summarized article. Search is {}.", searchText);
        try {
            LOGGER.info("Initiated new search for '{}'.", searchText);
            String text = indexer.searchIndex(searchText);
            LOGGER.info("Retrieval of summarized article successfully completed.");
            return Summarizer.summarize(text).toString();
        } catch (Exception ex) {
            return "Could not retrieve summarized article." + ex.toString();
        }
    }
}