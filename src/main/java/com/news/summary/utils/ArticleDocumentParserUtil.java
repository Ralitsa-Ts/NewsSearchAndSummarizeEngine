package com.news.summary.utils;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArticleDocumentParserUtil extends BaseDocumentParserUtil {

    private static final String P_ELEMENT_TAG = "p";
    private static final String ARTICLE_PAGE_TYPE = "article";
    private static final String ELEMENT_CLASS_STORY_BODY_INTRODUCTION = "story-body__introduction";
    private static final String PROPERTY_ATTRIBUTE_ARTICLE_BODY_VALUE = "articleBody";
    private static final String PROPERTY_ATTRIBUTE_ARTICLE_SECTION_VALUE = "article:section";

    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleDocumentParserUtil.class);

    public static String retrieveArticleStoryIntroduction(Document doc) {
        Element storyIntroduction = doc.getElementsByClass(
                        ELEMENT_CLASS_STORY_BODY_INTRODUCTION).first();
        return storyIntroduction != null ? storyIntroduction.text() : null;
    }

    public static String retrieveArticleMainStory(Document doc) {
        Element articleStoryBody = doc.getElementsByAttributeValue(PROPERTY_ATTRIBUTE,
                        PROPERTY_ATTRIBUTE_ARTICLE_BODY_VALUE).first();
        if (articleStoryBody != null) {
            String articleMainStory = "";
            for (Element articleStoryBodyElement : articleStoryBody
                            .getElementsByTag(P_ELEMENT_TAG)) {
                if (!articleStoryBodyElement.hasClass(ELEMENT_CLASS_STORY_BODY_INTRODUCTION)) {
                    articleMainStory = StringUtils.appendIfMissing(articleMainStory,
                                    articleStoryBodyElement.text());
                }
            }
            return articleMainStory;
        }
        LOGGER.debug("No article main story was found.");
        return null;
    }

    public static String retrieveArticleSection(Document doc) {
        Element articleSection = doc.getElementsByAttributeValue(PROPERTY_ATTRIBUTE,
                        PROPERTY_ATTRIBUTE_ARTICLE_SECTION_VALUE).first();
        return articleSection != null ? articleSection.text() : null;
    }

    public static boolean isArticle(Document doc) {
        return retrievePageType(doc).equals(ARTICLE_PAGE_TYPE);
    }

    public static String getArticlePageType() {
        return ARTICLE_PAGE_TYPE;
    }
}
