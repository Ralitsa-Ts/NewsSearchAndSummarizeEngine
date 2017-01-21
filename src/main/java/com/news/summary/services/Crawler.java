package com.news.summary.services;

import java.util.List;

import org.springframework.stereotype.Component;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

@Component
public class Crawler {
	
	private static final String CRAWLER_STORAGE_FOLDER = "/tmp/crawl/news";
	private static final int NUMBER_OF_CRAWLERS = 7;
	
    public void crawl(List<String> seeds) throws Exception {

        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(CRAWLER_STORAGE_FOLDER);

        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

        for (String seed : seeds) {
        	controller.addSeed(seed);
        }
        controller.start(CrawlerService.class, NUMBER_OF_CRAWLERS);
    }
}
