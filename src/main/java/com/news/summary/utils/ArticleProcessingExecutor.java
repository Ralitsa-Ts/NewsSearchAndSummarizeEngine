package com.news.summary.utils;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.uuid.Generators;
import com.news.summary.models.Article;
import com.news.summary.repositories.ArticlesRepository;

@Component
public class ArticleProcessingExecutor {

    private static final String SPACE_DELIMETER = " ";
    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleProcessingExecutor.class);

    @Autowired
    private ArticlesRepository articlesRepository;

    public void processArticle(Document document) {
        LOGGER.info("Processing article with URL {}.", document.location());
        String articleTitle = document.title();
        String articleMainStory = ArticleDocumentParserUtil.retrieveArticleMainStory(document);
        String articleStoryIntroduction = ArticleDocumentParserUtil
                        .retrieveArticleStoryIntroduction(document);
        String articleStory = StringUtils.join(
                        Arrays.asList(articleTitle, articleStoryIntroduction, articleMainStory),
                        SPACE_DELIMETER);
        List<String> keywords = KeywordsExtractor.getKeywordStringsList(articleStory);
        Article article = new Article(Generators.timeBasedGenerator().generate().toString(),
                        document.location(), StringUtils.join(keywords, SPACE_DELIMETER));
        if (article != null) {
            articlesRepository.save(article);
        }
    }
}