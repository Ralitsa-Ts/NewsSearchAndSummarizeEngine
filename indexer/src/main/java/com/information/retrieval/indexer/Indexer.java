package com.information.retrieval.indexer;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.stereotype.Component;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.classic.QueryParser.Operator;

import com.information.retrieval.indexer.util.ArticleDocumentParserUtil;

@Component
public class Indexer {

    public synchronized void addDoc(org.jsoup.nodes.Document document) throws IOException {
        Directory index = null;
        try {
            index = FSDirectory.open(Paths.get("/tmp/indexer"));
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        StandardAnalyzerWithStemming analyzer = new StandardAnalyzerWithStemming();

        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter indexWriter = null;
        try {
           indexWriter = new IndexWriter(index, config);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Document doc = new Document();
        String title = document.title();
        String url = document.location();
        String articleMainStory = ArticleDocumentParserUtil.retrieveArticleMainStory(document);
        String articleStoryIntroduction = ArticleDocumentParserUtil
                        .retrieveArticleStoryIntroduction(document);
        if (StringUtils.isNotBlank(title)) {
            doc.add(new TextField("title", title, Field.Store.YES));
        }
        // Here, we use a string field for course_code to avoid tokenizing.
        if (StringUtils.isNotBlank(url)) {
            doc.add(new StringField("url", url, Field.Store.YES));
        }
        if (StringUtils.isNotBlank(articleMainStory)) {
            doc.add(new TextField("articleMainStory", articleMainStory, Field.Store.YES));
        }
        if (StringUtils.isNotBlank(articleStoryIntroduction)) {
            doc.add(new TextField("articleStoryIntroduction", ArticleDocumentParserUtil
                            .retrieveArticleStoryIntroduction(document), Field.Store.YES));
        }
        indexWriter.addDocument(doc);
        indexWriter.close();
    }
    
    public synchronized String searchIndex(String search) {
        Query q = null;
        try {
            MultiFieldQueryParser parser = new MultiFieldQueryParser(new String[] 
                            {"title", "articleMainStory", "articleStoryIntroduction"}, new StandardAnalyzerWithStemming());
            parser.setDefaultOperator(Operator.OR);
            q = parser.parse(search);
        } catch (ParseException e) {
            e.printStackTrace();
        } 
        int hitsPerPage = 10;
        IndexReader reader = null;
        try {
            reader = DirectoryReader.open(FSDirectory.open(Paths.get("/tmp/indexer")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        IndexSearcher searcher = new IndexSearcher(reader);
        TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage);
        try {
            searcher.search(q, collector);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ScoreDoc[] hits = collector.topDocs().scoreDocs;
        try {
            return searcher.doc(hits[0].doc).get("articleMainStory");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
