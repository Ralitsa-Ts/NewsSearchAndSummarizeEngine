package com.news.summary.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.uuid.Generators;
import com.news.summary.client.repository.ArticlesClient;
import com.news.summary.client.repository.SearchesClient;
import com.news.summary.models.Article;
import com.news.summary.models.Search;
import com.news.summary.models.SearchStatus;
import com.news.summary.services.crawler.articles.Crawler;
import com.news.summary.utils.ArticleDocumentParserUtil;
import com.news.summary.utils.KeywordsExtractor;

import uk.org.lidalia.slf4jext.LoggerFactory;

@Component
public class ArticleControllerHelper {

    private static final String SPACE_DELIMETER = " ";
    private static final String BBC_NEWS_URL = "http://www.bbc.com/news";
    private static final int CRAWLER_TIMEOUT_MINUTES = 3;
    private static final int NUMBER_OF_CRAWLER_THREADS = 7;

    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleControllerHelper.class);

    @Autowired
    private Crawler crawler;

    @Autowired
    private SearchesClient searchesClient;

    @Autowired
    private ArticlesClient articlesClient;

    public String findSummarizedArticle(Search search) {
        try {
            LOGGER.info("Searching for summarized text for search with id {}.", search.getId());
            crawler.crawl(Arrays.asList(BBC_NEWS_URL), CRAWLER_TIMEOUT_MINUTES,
                            NUMBER_OF_CRAWLER_THREADS);
            Article bestArticleMatchingSearch = findBestArticleMatchingSearch(search);
            if (bestArticleMatchingSearch == null) {
                throw new RuntimeException("No article matching the search was found.");
            }
            Document bestArticleDocument = Jsoup.connect(bestArticleMatchingSearch.getUrl()).get();
            String articleContent = ArticleDocumentParserUtil
                            .retrieveArticleContent(bestArticleDocument);
            /**
             * TODO: Instead of just returning the content here pass the content
             * to the summarization service.
             */
            return articleContent;
        } catch (Exception e) {
            String errorMessage = "Failed to find summarized article for seach with id {}."
                            + search.getId();
            LOGGER.error(errorMessage, e);
            throw new RuntimeException(errorMessage, e);
        }
    }

    public Search createSearch(String searchText) {
        List<String> keywords = KeywordsExtractor.getKeywordStringsList(searchText);
        Search search = new Search(Generators.timeBasedGenerator().generate().toString(),
                        StringUtils.join(keywords, SPACE_DELIMETER),
                        SearchStatus.IN_PROGRESS.toString());
        searchesClient.save(search);
        return search;
    }
    
    private Article findBestArticleMatchingSearch(Search search) {
        LOGGER.info("Retrieving all articles related to search with Id {}.", search.getId());
        List<Article> articles = articlesClient
                        .getArticlesBySearchId(search.getId());
        if (articles.isEmpty()) {
            return null;
        }
        List<Integer> matchingKeywordsAmounts = new ArrayList<>();
        for (Article article : articles) {
            List<String> keywordsSearch = new ArrayList<>(
                            Arrays.asList(search.getKeywords().split(SPACE_DELIMETER)));
            keywordsSearch.retainAll(Arrays.asList(article.getKeywords().split(SPACE_DELIMETER)));
            matchingKeywordsAmounts.add(keywordsSearch.size());
        }
        int bestIndex = 0;
        for (int i = 0; i < matchingKeywordsAmounts.size(); i++) {
            if (matchingKeywordsAmounts.get(i) > matchingKeywordsAmounts.get(bestIndex)) {
                bestIndex = i;
            }
        }
        return articles.get(bestIndex);
    }
}
