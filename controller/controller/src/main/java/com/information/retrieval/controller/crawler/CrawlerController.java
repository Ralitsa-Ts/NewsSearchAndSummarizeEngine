package com.information.retrieval.controller.crawler;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.information.retrieval.crawler.Crawler;

@Controller
public class CrawlerController {

    private static final String BBC_NEWS_URL = "http://www.bbc.com/news";
    private static final int CRAWLER_TIMEOUT_MINUTES = 1;
    private static final int NUMBER_OF_CRAWLER_THREADS = 7;
    private static final Logger LOGGER = LoggerFactory.getLogger(CrawlerController.class);
    
    @Autowired
    private Crawler crawler;
    
    @RequestMapping("/start-crawler")
    @ResponseBody
    public void startCrawler() {
        try {
            crawler.crawl(Arrays.asList(BBC_NEWS_URL), CRAWLER_TIMEOUT_MINUTES,
                            NUMBER_OF_CRAWLER_THREADS);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
