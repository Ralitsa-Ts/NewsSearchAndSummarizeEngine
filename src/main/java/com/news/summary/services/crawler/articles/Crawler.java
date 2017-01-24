package com.news.summary.services.crawler.articles;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.news.summary.services.crawler.ApplicationContextAwareCrawlerController;

@Component
public class Crawler {

	private static final Logger LOGGER = LoggerFactory
					.getLogger(Crawler.class);

	@Autowired
	private ApplicationContextAwareCrawlerController articleCrawler;

    public void crawl(List<String> seeds, int timeoutMinutes, int numberOfCrawlerThreads)
                    throws Exception {

		for (String seed : seeds) {
			LOGGER.info("Adding seed {}.", seed);
			articleCrawler.addSeed(seed);
		}
        LOGGER.info("Starting crawler with {} crawler threads", numberOfCrawlerThreads);
        articleCrawler.start(ArticleCrawlerService.class, numberOfCrawlerThreads);
        Thread.sleep(TimeUnit.MINUTES.toMillis(timeoutMinutes));
        LOGGER.info("Timeout of {} minutes reached. Shutting down the crawler.",
                        timeoutMinutes);
        articleCrawler.shutdown();
	}
}
