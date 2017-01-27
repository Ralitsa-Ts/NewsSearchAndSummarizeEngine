package com.information.retrieval.controller.summarized.article;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.information.retrieval.indexer.Indexer;
import com.information.retrieval.summarization.Summarizer;

@Controller
public class ArticleController {

	private static final int MAX_ARTICLES_TO_RETRIEVE_AMOUNT = 1;
    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleController.class);
    
    @Autowired
    private Indexer indexer;
    
    @CrossOrigin(origins = "http://localhost:9000")
    @RequestMapping("/find-summarized-article")
    @ResponseBody
    public String findSummarizedArticle(String searchText) {
        LOGGER.info("Received request for finding a summarized article. Search is {}.", searchText);
        try {
            LOGGER.info("Initiated new search for '{}'.", searchText);
            List<String> articleContents = indexer.searchIndex(searchText, MAX_ARTICLES_TO_RETRIEVE_AMOUNT);
            if (articleContents.isEmpty()) {
            	String errorMessage = "Could not find matching articles for search " + searchText;
            	LOGGER.info(errorMessage);
            	throw new InternalError(errorMessage);
            }
            String topArticleContent = articleContents.get(0);
            String summarizedTopArticleContent = Summarizer.summarize(topArticleContent).toString();
            LOGGER.info("Retrieval of summarized article successfully completed.");
            return summarizedTopArticleContent;
        } catch (Exception e) {
            return "Could not retrieve summarized article. Error:" + e;
        }
    }
    
    @CrossOrigin(origins = "http://localhost:9000")
    @RequestMapping("/find-article")
    @ResponseBody
    public String findArticle(String searchText) {
        LOGGER.info("Received request for finding an article. Search is {}.", searchText);
        try {
            LOGGER.info("Initiated new search for '{}'.", searchText);
            List<String> articleContents = indexer.searchIndex(searchText, MAX_ARTICLES_TO_RETRIEVE_AMOUNT);
            if (articleContents.isEmpty()) {
            	String errorMessage = "Could not find matching articles for search " + searchText;
            	LOGGER.info(errorMessage);
            	throw new InternalError(errorMessage);
            }
            String topArticleContent = articleContents.get(0);
            LOGGER.info("Retrieval of article successfully completed.");
            return topArticleContent;
        } catch (Exception e) {
            return "Could not retrieve article. Error:" + e;
        }
    }
}