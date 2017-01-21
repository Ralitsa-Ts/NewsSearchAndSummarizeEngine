package com.news.summary.services.crawler;

import org.springframework.stereotype.Component;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;

@Component
public class CrawlerConfiguration extends CrawlConfig {

	/**
	 * The folder which will be used by crawler for storing the intermediate
	 * crawl data. The content of this folder should not be modified manually.
	 */
	private String crawlStorageFolder = "/tmp/crawl";

	/**
	 * If this feature is enabled, you would be able to resume a previously
	 * stopped/crashed crawl. However, it makes crawling slightly slower
	 */
	private boolean resumableCrawling = false;

	/**
	 * Maximum depth of crawling For unlimited depth this parameter should be
	 * set to -1
	 */
	private int maxDepthOfCrawling = 3;

	/**
	 * Maximum number of pages to fetch For unlimited number of pages, this
	 * parameter should be set to -1
	 */
	private int maxPagesToFetch = -1;

	/**
	 * user-agent string that is used for representing your crawler to web
	 * servers. See http://en.wikipedia.org/wiki/User_agent for more details
	 */
	private String userAgentString = "crawler4j (http://code.google.com/p/crawler4j/)";

	/**
	 * Politeness delay in milliseconds (delay between sending two requests to
	 * the same host).
	 */
	private int politenessDelay = 200;

	/**
	 * Should we also crawl https pages?
	 */
	private boolean includeHttpsPages = false;

	/**
	 * Should we fetch binary content such as images, audio, ...?
	 */
	private boolean includeBinaryContentInCrawling = true;

	/**
	 * Maximum Connections per host
	 */
	private int maxConnectionsPerHost = 100;

	/**
	 * Maximum total connections
	 */
	private int maxTotalConnections = 100;

	/**
	 * Socket timeout in milliseconds
	 */
	private int socketTimeout = 20000;

	/**
	 * Connection timeout in milliseconds
	 */
	private int connectionTimeout = 30000;

	/**
	 * Max number of outgoing links which are processed from a page
	 */
	private int maxOutgoingLinksToFollow = 5000;

	/**
	 * Max allowed size of a page. Pages larger than this size will not be
	 * fetched.
	 */
	private int maxDownloadSize = 1048576;

	/**
	 * Should we follow redirects?
	 */
	private boolean followRedirects = true;

	/**
	 * If crawler should run behind a proxy, this parameter can be used for
	 * specifying the proxy host.
	 */
	private String proxyHost = null;

	/**
	 * If crawler should run behind a proxy, this parameter can be used for
	 * specifying the proxy port.
	 */
	private int proxyPort = 80;

	/**
	 * If crawler should run behind a proxy and user/pass is needed for
	 * authentication in proxy, this parameter can be used for specifying the
	 * username.
	 */
	private String proxyUsername = null;

	/**
	 * If crawler should run behind a proxy and user/pass is needed for
	 * authentication in proxy, this parameter can be used for specifying the
	 * password.
	 */
	private String proxyPassword = null;

	public CrawlerConfiguration() {
	}

	/**
	 * Validates the configs specified by this instance.
	 * 
	 * @throws Exception
	 */
	@Override
	public void validate() throws Exception {
		if (crawlStorageFolder == null) {
			throw new Exception("Crawl storage folder is not set in the CrawlConfig.");
		}
		if (politenessDelay < 0) {
			throw new Exception("Invalid value for politeness delay: " + politenessDelay);
		}
		if (maxDepthOfCrawling < -1) {
			throw new Exception("Maximum crawl depth should be either a positive number or -1 for unlimited depth.");
		}
		if (maxDepthOfCrawling > Short.MAX_VALUE) {
			throw new Exception("Maximum value for crawl depth is " + Short.MAX_VALUE);
		}

	}

	@Override
	public String getCrawlStorageFolder() {
		return crawlStorageFolder;
	}

	/**
	 * The folder which will be used by crawler for storing the intermediate
	 * crawl data. The content of this folder should not be modified manually.
	 */
	@Override
	public void setCrawlStorageFolder(String crawlStorageFolder) {
		this.crawlStorageFolder = crawlStorageFolder;
	}

	@Override
	public boolean isResumableCrawling() {
		return resumableCrawling;
	}

	/**
	 * If this feature is enabled, you would be able to resume a previously
	 * stopped/crashed crawl. However, it makes crawling slightly slower
	 */
	@Override
	public void setResumableCrawling(boolean resumableCrawling) {
		this.resumableCrawling = resumableCrawling;
	}

	@Override
	public int getMaxDepthOfCrawling() {
		return maxDepthOfCrawling;
	}

	/**
	 * Maximum depth of crawling For unlimited depth this parameter should be
	 * set to -1
	 */
	@Override
	public void setMaxDepthOfCrawling(int maxDepthOfCrawling) {
		this.maxDepthOfCrawling = maxDepthOfCrawling;
	}

	@Override
	public int getMaxPagesToFetch() {
		return maxPagesToFetch;
	}

	/**
	 * Maximum number of pages to fetch For unlimited number of pages, this
	 * parameter should be set to -1
	 */
	@Override
	public void setMaxPagesToFetch(int maxPagesToFetch) {
		this.maxPagesToFetch = maxPagesToFetch;
	}

	@Override
	public String getUserAgentString() {
		return userAgentString;
	}

	/**
	 * user-agent string that is used for representing your crawler to web
	 * servers. See http://en.wikipedia.org/wiki/User_agent for more details
	 */
	@Override
	public void setUserAgentString(String userAgentString) {
		this.userAgentString = userAgentString;
	}

	@Override
	public int getPolitenessDelay() {
		return politenessDelay;
	}

	/**
	 * Politeness delay in milliseconds (delay between sending two requests to
	 * the same host).
	 * 
	 * @param politenessDelay
	 *            the delay in milliseconds.
	 */
	@Override
	public void setPolitenessDelay(int politenessDelay) {
		this.politenessDelay = politenessDelay;
	}

	@Override
	public boolean isIncludeHttpsPages() {
		return includeHttpsPages;
	}

	/**
	 * Should we also crawl https pages?
	 */
	@Override
	public void setIncludeHttpsPages(boolean includeHttpsPages) {
		this.includeHttpsPages = includeHttpsPages;
	}

	@Override
	public boolean isIncludeBinaryContentInCrawling() {
		return includeBinaryContentInCrawling;
	}

	/**
	 * Should we fetch binary content such as images, audio, ...?
	 */
	@Override
	public void setIncludeBinaryContentInCrawling(boolean includeBinaryContentInCrawling) {
		this.includeBinaryContentInCrawling = includeBinaryContentInCrawling;
	}

	@Override
	public int getMaxConnectionsPerHost() {
		return maxConnectionsPerHost;
	}

	/**
	 * Maximum Connections per host
	 */
	@Override
	public void setMaxConnectionsPerHost(int maxConnectionsPerHost) {
		this.maxConnectionsPerHost = maxConnectionsPerHost;
	}

	@Override
	public int getMaxTotalConnections() {
		return maxTotalConnections;
	}

	/**
	 * Maximum total connections
	 */
	@Override
	public void setMaxTotalConnections(int maxTotalConnections) {
		this.maxTotalConnections = maxTotalConnections;
	}

	@Override
	public int getSocketTimeout() {
		return socketTimeout;
	}

	/**
	 * Socket timeout in milliseconds
	 */
	@Override
	public void setSocketTimeout(int socketTimeout) {
		this.socketTimeout = socketTimeout;
	}

	@Override
	public int getConnectionTimeout() {
		return connectionTimeout;
	}

	/**
	 * Connection timeout in milliseconds
	 */
	@Override
	public void setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	@Override
	public int getMaxOutgoingLinksToFollow() {
		return maxOutgoingLinksToFollow;
	}

	/**
	 * Max number of outgoing links which are processed from a page
	 */
	@Override
	public void setMaxOutgoingLinksToFollow(int maxOutgoingLinksToFollow) {
		this.maxOutgoingLinksToFollow = maxOutgoingLinksToFollow;
	}

	@Override
	public int getMaxDownloadSize() {
		return maxDownloadSize;
	}

	/**
	 * Max allowed size of a page. Pages larger than this size will not be
	 * fetched.
	 */
	@Override
	public void setMaxDownloadSize(int maxDownloadSize) {
		this.maxDownloadSize = maxDownloadSize;
	}

	@Override
	public boolean isFollowRedirects() {
		return followRedirects;
	}

	/**
	 * Should we follow redirects?
	 */
	@Override
	public void setFollowRedirects(boolean followRedirects) {
		this.followRedirects = followRedirects;
	}

	@Override
	public String getProxyHost() {
		return proxyHost;
	}

	/**
	 * If crawler should run behind a proxy, this parameter can be used for
	 * specifying the proxy host.
	 */
	@Override
	public void setProxyHost(String proxyHost) {
		this.proxyHost = proxyHost;
	}

	@Override
	public int getProxyPort() {
		return proxyPort;
	}

	/**
	 * If crawler should run behind a proxy, this parameter can be used for
	 * specifying the proxy port.
	 */
	@Override
	public void setProxyPort(int proxyPort) {
		this.proxyPort = proxyPort;
	}

	@Override
	public String getProxyUsername() {
		return proxyUsername;
	}

	/**
	 * If crawler should run behind a proxy and user/pass is needed for
	 * authentication in proxy, this parameter can be used for specifying the
	 * username.
	 */
	@Override
	public void setProxyUsername(String proxyUsername) {
		this.proxyUsername = proxyUsername;
	}

	@Override
	public String getProxyPassword() {
		return proxyPassword;
	}

	/**
	 * If crawler should run behind a proxy and user/pass is needed for
	 * authentication in proxy, this parameter can be used for specifying the
	 * password.
	 */
	@Override
	public void setProxyPassword(String proxyPassword) {
		this.proxyPassword = proxyPassword;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Crawl storage folder: " + getCrawlStorageFolder() + "\n");
		sb.append("Resumable crawling: " + isResumableCrawling() + "\n");
		sb.append("Max depth of crawl: " + getMaxDepthOfCrawling() + "\n");
		sb.append("Max pages to fetch: " + getMaxPagesToFetch() + "\n");
		sb.append("User agent string: " + getUserAgentString() + "\n");
		sb.append("Include https pages: " + isIncludeHttpsPages() + "\n");
		sb.append("Include binary content: " + isIncludeBinaryContentInCrawling() + "\n");
		sb.append("Max connections per host: " + getMaxConnectionsPerHost() + "\n");
		sb.append("Max total connections: " + getMaxTotalConnections() + "\n");
		sb.append("Socket timeout: " + getSocketTimeout() + "\n");
		sb.append("Max total connections: " + getMaxTotalConnections() + "\n");
		sb.append("Max outgoing links to follow: " + getMaxOutgoingLinksToFollow() + "\n");
		sb.append("Max download size: " + getMaxDownloadSize() + "\n");
		sb.append("Should follow redirects?: " + isFollowRedirects() + "\n");
		sb.append("Proxy host: " + getProxyHost() + "\n");
		sb.append("Proxy port: " + getProxyPort() + "\n");
		sb.append("Proxy username: " + getProxyUsername() + "\n");
		sb.append("Proxy password: " + getProxyPassword() + "\n");
		return sb.toString();
	}

}