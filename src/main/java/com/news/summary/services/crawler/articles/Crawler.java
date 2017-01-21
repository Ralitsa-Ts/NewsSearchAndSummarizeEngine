package com.news.summary.services.crawler.articles;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.news.summary.services.crawler.ApplicationContextAwareCrawlerController;

@Component
public class Crawler {

	private static final int NUMBER_OF_CRAWLER_THREADS = 7;
	private static final Logger LOGGER = LoggerFactory
					.getLogger(Crawler.class);

	@Autowired
	private ApplicationContextAwareCrawlerController articleCrawler;

	public void crawl(List<String> seeds) throws Exception {

		for (String seed : seeds) {
			LOGGER.info("Adding seed {}.", seed);
			articleCrawler.addSeed(seed);
		}
		LOGGER.info("Starting crawler with {} crawler threads", NUMBER_OF_CRAWLER_THREADS);
		articleCrawler.start(ArticleCrawlerService.class, NUMBER_OF_CRAWLER_THREADS);
	}
}
