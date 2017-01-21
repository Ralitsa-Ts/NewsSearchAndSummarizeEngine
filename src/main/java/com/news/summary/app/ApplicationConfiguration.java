package com.news.summary.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.news.summary.services.crawler.CrawlerConfiguration;

import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

@Configuration
public class ApplicationConfiguration {

	@Autowired
	private CrawlerConfiguration articleCrawlerConfig;

	@Autowired
	private PageFetcher pageFetcher;

	@Bean
	public PageFetcher getPageFetcher() {
		return new PageFetcher(articleCrawlerConfig);  
	}

	@Bean
	public RobotstxtServer getRobotstxtServer() {
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		return new RobotstxtServer(robotstxtConfig, pageFetcher);
	}
}
