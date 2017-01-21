package com.news.summary.controllers;

import java.util.Arrays;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.news.summary.services.crawler.articles.Crawler;

import uk.org.lidalia.slf4jext.LoggerFactory;

@Controller
public class NewsSummarizationController {

	private static final Logger LOGGER = LoggerFactory.getLogger(NewsSummarizationController.class);

	@Autowired
	private Crawler crawler;

	@RequestMapping("/find-summarized-article")
	@ResponseBody
	public String findSummarizedArticle(String search) {
		LOGGER.info("Received request for finding a summarized article. Search is {}.", search);
		try {
			crawler.crawl(Arrays.asList("http://www.bbc.com/news"));
		} catch (Exception ex) {
			return "Could not retrieve summarized article." + ex.toString();
		}
		return "Summarized article successfully retrieved.";
	}
}