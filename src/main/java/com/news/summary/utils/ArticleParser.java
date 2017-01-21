package com.news.summary.utils;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.news.summary.models.Article;

@Component
public class ArticleParser {

	private static final String P_ELEMENT_TAG = "p";
	private static final String ARTICLE_PAGE_TYPE = "article";
	private static final String ELEMENT_CLASS_STORY_BODY_INTRODUCTION = "story-body__introduction";
	private static final String PROPERTY_ATTRIBUTE_ARTICLE_BODY_VALUE = "articleBody";
	private static final String PROPERTY_ATTRIBUTE_ARTICLE_SECTION_VALUE = "article:section";
	private static final String PROPERTY_ATTRIBUTE_OG_TYPE_VALUE = "og:type";
	private static final String PROPERTY_ATTRIBUTE = "property";
	private static final String CONTENT_ATTRIBUTE = "content";
	private static final Logger LOGGER = LoggerFactory.getLogger(ArticleParser.class);

	public Article parseDocument(String htmlDocument, String url) {
		Document doc = Jsoup.parse( htmlDocument, url );	
		String pageType = retrievePageType(doc);
		if (StringUtils.isNotBlank(pageType) && pageType.equals(ARTICLE_PAGE_TYPE)) {
			LOGGER.info("Retrieved new document of type article. URL is {}.", url);
			String articleMainStory = retrieveArticleMainStory(doc);
			if (StringUtils.isBlank(articleMainStory)) {
				LOGGER.error("Retrieved article has no main story. URL is {}.", url);
				return null;
			}
			String articleStoryIntroduction = retrieveArticleStoryIntroduction(doc);
			String articleSection = retrieveArticleSection(doc);
			return new Article(articleStoryIntroduction, articleMainStory, 
							articleSection, pageType, url, doc.title());
		} else {
			LOGGER.debug("Observed document is not of type article. Type is {}. URL is {}.",
							pageType, url);
			return null;
		}
	}

	private String retrieveArticleStoryIntroduction(Document doc) {
		Element storyIntroduction = doc.getElementsByClass(
						ELEMENT_CLASS_STORY_BODY_INTRODUCTION).first();
		return storyIntroduction != null ? storyIntroduction.text() : null;
	}

	private String retrieveArticleMainStory(Document doc) {
		Element articleStoryBody = doc.getElementsByAttributeValue(PROPERTY_ATTRIBUTE, 
						PROPERTY_ATTRIBUTE_ARTICLE_BODY_VALUE).first();
		if (articleStoryBody != null) {
			String articleMainStory = "";
			for (Element articleStoryBodyElement : articleStoryBody.getElementsByTag(P_ELEMENT_TAG)) {
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

	private String retrieveArticleSection(Document doc) {
		Element articleSection = doc.getElementsByAttributeValue(PROPERTY_ATTRIBUTE,
						PROPERTY_ATTRIBUTE_ARTICLE_SECTION_VALUE).first();
		return articleSection != null ? articleSection.text() : null;
	}

	private String retrievePageType(Document doc) {
		Element pageType = doc.getElementsByAttributeValue(PROPERTY_ATTRIBUTE,
						PROPERTY_ATTRIBUTE_OG_TYPE_VALUE).first();
		return pageType != null ? pageType.attr(CONTENT_ATTRIBUTE) : null;
	}
}