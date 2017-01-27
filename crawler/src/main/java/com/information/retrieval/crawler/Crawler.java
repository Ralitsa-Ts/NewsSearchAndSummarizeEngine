package com.information.retrieval.crawler;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Crawler {

	private static final Logger LOGGER = LoggerFactory
					.getLogger(Crawler.class);

    @Autowired
	private ApplicationContextAwareCrawlerController articleCrawler;

    public void crawl(List<String> seeds, int numberOfCrawlerThreads)
                    throws Exception {

		for (String seed : seeds) {
			LOGGER.info("Adding seed {}.", seed);
			articleCrawler.addSeed(seed);
		}
        LOGGER.info("Starting crawler with {} crawler threads", numberOfCrawlerThreads);
        articleCrawler.start(CrawlerService.class, numberOfCrawlerThreads);
	}
}
