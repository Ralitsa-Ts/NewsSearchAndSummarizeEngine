package com.information.retrieval.crawler;


import java.io.IOException;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.information.retrieval.indexer.Indexer;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

@Component
public class ArticleCrawlerService extends WebCrawler {

	private static final Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg"
					+ "|png|mp3|mp3|zip|gz))$");
    private static final String HTTP_URL_BEGGINING = "http://www.bbc.com/";
	private static final Logger LOGGER = LoggerFactory
					.getLogger(ArticleCrawlerService.class);

	@Autowired
    private Indexer indexer;

	/**
	 * Specifies if the new url should be crawled or not. Here the crawler
	 * ignores urls that have css, js, git and start with something else except
	 * 'http://'
	 * 
	 * @param referringPage
	 *            page in which the new url was discovered, cannot be null.
	 * @param url
	 *            new url, cannot be null.
	 * @return true if the new url should be crawled and false otherwise.
	 */
	@Override
	public boolean shouldVisit(Page referringPage, WebURL url) {
		Validate.notNull(referringPage);
		Validate.notNull(url);
		String href = url.getURL().toLowerCase();
		return !FILTERS.matcher(href).matches() && href.startsWith(HTTP_URL_BEGGINING);
	}

	/**
	 * This function is called when a page is fetched and ready to be processed.
	 * 
	 * @param page
	 *            is the page to be visited, cannot be null.
	 */
	@Override
	public void visit(Page page) {
		Validate.notNull(page);
		String url = page.getWebURL().getURL();
		LOGGER.info("Visiting URL {}.", url);

		if (page.getParseData() instanceof HtmlParseData) {
			HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            Document document = Jsoup.parse(htmlParseData.getHtml(), url);
            try {
                indexer.addDoc(document);
            } catch (IOException e) {
                LOGGER.error("Failed to add document with url {}.", document.location());
            }
		}
	}
}