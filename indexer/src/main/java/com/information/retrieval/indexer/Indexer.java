package com.information.retrieval.indexer;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser.Operator;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.information.retrieval.indexer.util.ArticleDocumentParserUtil;

@Component
public class Indexer {

	private static final String TITLE_DOC_FIELD = "title";
	private static final String URL_DOC_FIELD = "url";
	private static final String ARTICLE_STORY_INTRODUCTION_DOC_FIELD = "articleStoryIntroduction";
	private static final String ARTICLE_MAIN_STORY_DOC_FIELD = "articleMainStory";
	private static final String INDEXER_DIR = "/tmp/indexer";
	private static final int HITS_PER_PAGE = 10;
	private static final Logger LOGGER = LoggerFactory.getLogger(Indexer.class);

	public synchronized void addDoc(org.jsoup.nodes.Document document) throws IOException {
		Document doc = createDocument(document);
		if (doc != null) {
			IndexWriter indexWriter = createIndexWriter();
			indexWriter.addDocument(doc);
			indexWriter.close();
		}
	}

	/**
	 * Retrieve the content of the first numDocs which match the search.
	 */
	public synchronized List<String> searchIndex(String search, int numDocs) {
		Query query = createQuery(search);
		IndexReader reader = null;
		try {
			reader = DirectoryReader.open(FSDirectory.open(Paths.get(INDEXER_DIR)));
		} catch (Exception e) {
			String errorMessage = "Failed to open directory " + INDEXER_DIR + ".";
			LOGGER.error(errorMessage, e);
			throw new InternalError(errorMessage);
		}
		IndexSearcher searcher = new IndexSearcher(reader);
		TopScoreDocCollector collector = TopScoreDocCollector.create(HITS_PER_PAGE);
		try {
			searcher.search(query, collector);
			ScoreDoc[] hits = collector.topDocs().scoreDocs;
			List<String> docsContent = new ArrayList<>();
			for (int i = 0, docNum = 0; i < hits.length && docNum < numDocs; i++) {
				Document doc = searcher.doc(hits[i].doc);
				String articleIntroduction = doc.get(ARTICLE_STORY_INTRODUCTION_DOC_FIELD);
				String articleMainStory = doc.get(ARTICLE_MAIN_STORY_DOC_FIELD);
				if (StringUtils.isNotBlank(articleIntroduction) && StringUtils.isNotBlank(articleMainStory)) {
					String articleStory = articleIntroduction.concat(articleMainStory);
					docsContent.add(articleStory);
					docNum++;
				}
			}
			return docsContent;
		} catch (Exception e) {
			String errorMessage = "Failed while searching the index for " + search + "."; 
			LOGGER.error(errorMessage, e);
			throw new InternalError(errorMessage);
		}
	}
	
	private Query createQuery(String search) {
		Query query = null;
		try {
			String[] fields = new String[] 
					{TITLE_DOC_FIELD, ARTICLE_MAIN_STORY_DOC_FIELD, 
							ARTICLE_STORY_INTRODUCTION_DOC_FIELD};
			MultiFieldQueryParser parser = new MultiFieldQueryParser(fields,
					new StandardAnalyzerWithStemming());
			parser.setDefaultOperator(Operator.OR);
			query = parser.parse(search);
		} catch (ParseException e) {
			String errorMessage = "Failed to create query for search " + search + ".";
			LOGGER.error(errorMessage);
			throw new InternalError(errorMessage, e);
		} 
		return query;
	}

	private IndexWriter createIndexWriter() {
		Directory index = null;
		try {
			index = FSDirectory.open(Paths.get(INDEXER_DIR));
		} catch (IOException e) {
			String errorMessage = "Failed to open file directory " + INDEXER_DIR + ".";
			LOGGER.error(errorMessage);
			throw new InternalError(errorMessage, e);
		}
		StandardAnalyzerWithStemming analyzer = new StandardAnalyzerWithStemming();
		IndexWriterConfig config = new IndexWriterConfig(analyzer);
		IndexWriter indexWriter = null;
		try {
			indexWriter = new IndexWriter(index, config);
		} catch (IOException e) {
			String errorMessage = "Failed to create index writer.";
			LOGGER.error(errorMessage);
			throw new InternalError(errorMessage, e);
		}
		return indexWriter;
	}

	private Document createDocument(org.jsoup.nodes.Document document) {
		Document doc = new Document();
		String title = document.title();
		String url = document.location();
		String articleMainStory = ArticleDocumentParserUtil.retrieveArticleMainStory(document);
		String articleStoryIntroduction = ArticleDocumentParserUtil
				.retrieveArticleStoryIntroduction(document);
		if (StringUtils.isBlank(articleMainStory) && StringUtils.isBlank(articleStoryIntroduction)) {
			LOGGER.info("Document with url {} has no introduction and main story.", document.location());
			return null;
		}
		if (StringUtils.isNotBlank(title)) {
			doc.add(new TextField(TITLE_DOC_FIELD, title, Field.Store.YES));
		}
		// Here, we use a string field for course_code to avoid tokenizing.
		if (StringUtils.isNotBlank(url)) {
			doc.add(new StringField(URL_DOC_FIELD, url, Field.Store.YES));
		}
		if (StringUtils.isNotBlank(articleMainStory)) {
			doc.add(new TextField(ARTICLE_MAIN_STORY_DOC_FIELD, articleMainStory, Field.Store.YES));
		}
		if (StringUtils.isNotBlank(articleStoryIntroduction)) {
			doc.add(new TextField(ARTICLE_STORY_INTRODUCTION_DOC_FIELD, ArticleDocumentParserUtil
					.retrieveArticleStoryIntroduction(document), Field.Store.YES));
		}
		return doc;
	}
}
