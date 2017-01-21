package com.news.summary.services.crawler;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

@Component
public class ApplicationContextAwareCrawlerController extends CrawlController implements ApplicationContextAware {

	private final static Logger LOGGER = LoggerFactory.getLogger(ApplicationContextAwareCrawlerController.class);
	private ApplicationContext applicationContext;

	@Autowired
	public ApplicationContextAwareCrawlerController(CrawlConfig config, PageFetcher pageFetcher,
					RobotstxtServer robotstxtServer) throws Exception {
		super(config, pageFetcher, robotstxtServer);
	}

	@Override
	public <T extends WebCrawler> void start(final Class<T> _c, final int numberOfCrawlers) {
		try {
			finished = false;
			crawlersLocalData.clear();
			final List<Thread> threads = new ArrayList<Thread>();
			final List<T> crawlers = new ArrayList<T>();

			startCrawlerThreads(_c, numberOfCrawlers, threads, crawlers);

			final CrawlController controller = this;

			Thread monitorThread = new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						synchronized (waitingLock) {

							while (true) {
								sleep(10);
								boolean someoneIsWorking = false;
								for (int i = 0; i < threads.size(); i++) {
									Thread thread = threads.get(i);
									if (!thread.isAlive()) {
										if (!shuttingDown) {
											LOGGER.info("Thread " + i
															+ " was dead, I'll recreate it.");
											T crawler = _c.newInstance();
											thread = new Thread(crawler, "Crawler " + (i + 1));
											threads.remove(i);
											threads.add(i, thread);
											crawler.setThread(thread);
											crawler.init(i + 1, controller);
											thread.start();
											crawlers.remove(i);
											crawlers.add(i, crawler);
										}
									} else if (crawlers.get(i).isNotWaitingForNewURLs()) {
										someoneIsWorking = true;
									}
								}
								if (!someoneIsWorking) {
									// Make sure again that none of the threads
									// are
									// alive.
									LOGGER.info("It looks like no thread is working, waiting for 10 seconds to make sure...");
									sleep(10);

									someoneIsWorking = false;
									for (int i = 0; i < threads.size(); i++) {
										Thread thread = threads.get(i);
										if (thread.isAlive() && crawlers.get(i)
														.isNotWaitingForNewURLs()) {
											someoneIsWorking = true;
										}
									}
									if (!someoneIsWorking) {
										if (!shuttingDown) {
											long queueLength = frontier.getQueueLength();
											if (queueLength > 0) {
												continue;
											}
											LOGGER.info("No thread is working and no more URLs are in queue waiting for another 10 seconds to make sure...");
											sleep(10);
											queueLength = frontier.getQueueLength();
											if (queueLength > 0) {
												continue;
											}
										}

										LOGGER.info("All of the crawlers are stopped. Finishing the process...");
										// At this step, frontier notifies the
										// threads that were
										// waiting for new URLs and they should
										// stop
										frontier.finish();
										for (T crawler : crawlers) {
											crawler.onBeforeExit();
											crawlersLocalData.add(crawler.getMyLocalData());
										}

										LOGGER.info("Waiting for 10 seconds before final clean up...");
										sleep(10);

										frontier.close();
										docIdServer.close();
										pageFetcher.shutDown();

										finished = true;
										waitingLock.notifyAll();

										return;
									}
								}
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});

			monitorThread.start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private <T extends WebCrawler> void startCrawlerThreads(final Class<T> _c,
					final int numberOfCrawlers, final List<Thread> threads,
					final List<T> crawlers) {
		for (int i = 1; i <= numberOfCrawlers; i++) {
			LOGGER.error("Application context: " + applicationContext);
			T crawler = applicationContext.getBean(_c);
			Thread thread = new Thread(crawler, "Crawler " + i);
			crawler.setThread(thread);
			crawler.init(i, this);
			thread.start();
			crawlers.add(crawler);
			threads.add(thread);
			LOGGER.info("Crawler " + i + " started.");
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext arg0)
					throws BeansException {
		this.applicationContext = arg0;
	}
}
