package com.information.retrieval.indexer.util;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArticleDocumentParserUtil extends BaseDocumentParserUtil {

    private static final String P_ELEMENT_TAG = "p";
    private static final String ARTICLE_PAGE_TYPE = "article";
    private static final String ELEMENT_CLASS_STORY_BODY_INTRODUCTION_VALUE = "story-body__introduction";
    private static final String PROPERTY_ATTRIBUTE_ARTICLE_BODY_VALUE = "articleBody";
    private static final String ELEMENT_CLASS_INFORMATION_VALUE = "mini-info-list__item";
    private static final String ELEMENT_CLASS_DATE_VALUE = "date date--v2";
    private static final String ATTRIBUTE_DATA_SECONDS = "data-seconds";

    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleDocumentParserUtil.class);

    public static String retrieveArticleContent(Document doc) {
        String title = retrieveTitle(doc);
        String articleStoryIntroduction = retrieveArticleStoryIntroduction(doc);
        String articleMainStory = retrieveArticleMainStory(doc);
        String articleContent = title.concat(articleStoryIntroduction).concat(articleMainStory);
        return articleContent;
    }

    public static boolean isArticle(Document doc) {
        return retrievePageType(doc).equals(ARTICLE_PAGE_TYPE);
    }

    public static String getArticlePageType() {
        return ARTICLE_PAGE_TYPE;
    }

    public static String retrieveArticleStoryIntroduction(Document doc) {
        Element storyIntroduction = doc.getElementsByClass(
                        ELEMENT_CLASS_STORY_BODY_INTRODUCTION_VALUE).first();
        return storyIntroduction != null ? storyIntroduction.text() : null;
    }

    public static String retrieveArticleMainStory(Document doc) {
        Element articleStoryBody = doc.getElementsByAttributeValue(PROPERTY_ATTRIBUTE,
                        PROPERTY_ATTRIBUTE_ARTICLE_BODY_VALUE).first();
        if (articleStoryBody != null) {
            String articleMainStory = "";
            for (Element articleStoryBodyElement : articleStoryBody
                            .getElementsByTag(P_ELEMENT_TAG)) {
                if (!articleStoryBodyElement.hasClass(ELEMENT_CLASS_STORY_BODY_INTRODUCTION_VALUE)) {
                    articleMainStory = StringUtils.appendIfMissing(articleMainStory,
                                    articleStoryBodyElement.text());
                }
            }
            return articleMainStory;
        }
        LOGGER.debug("No article main story was found.");
        return null;
    }

    public static Long retrieveLastUpdatedEpochTime(Document doc) {
        Element articleInformation = doc.getElementsByClass(ELEMENT_CLASS_INFORMATION_VALUE)
                        .first();
        if (articleInformation != null) {
            Element lastUpdatedEpochTime = articleInformation
                            .getElementsByClass(ELEMENT_CLASS_DATE_VALUE).first();
            if (lastUpdatedEpochTime != null) {
                return Long.valueOf(lastUpdatedEpochTime.attr(ATTRIBUTE_DATA_SECONDS));
            }
        }
        return null;
    }
}
