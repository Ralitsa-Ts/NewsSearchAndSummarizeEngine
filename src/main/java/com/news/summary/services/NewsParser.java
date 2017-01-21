package com.news.summary.services;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import com.news.summary.models.Article;

@Component
public class NewsParser {
		
	public Article parseDocument(String htmlDocument, String url) {
		Document doc = Jsoup.parse( htmlDocument, url );	
		String title = doc.title();
		System.out.println("Title: " + title);
		Element articleStory = doc.select("div.story-body__inner").first();
		String articleStoryIntroduction = null;
		String articleMainStory = "";
		for (Element storyElement : articleStory.getElementsByTag("p")) {
			if (storyElement.hasClass("story-body__introduction")) {
				articleStoryIntroduction = storyElement.text();
			} else {
				articleMainStory = StringUtils.appendIfMissing(articleMainStory, storyElement.text());
			}
		}
		System.out.println("Article introduction: " + articleStoryIntroduction);
		System.out.println("Article Main story: " + articleMainStory);
		Element articleSection = doc.getElementsByAttributeValue("property", "article:section").first();
		Element pageType = doc.getElementsByAttributeValue("ob:type", "article").first();
		return new Article(articleStoryIntroduction, articleMainStory, articleSection.text(), pageType.text());
	}
}