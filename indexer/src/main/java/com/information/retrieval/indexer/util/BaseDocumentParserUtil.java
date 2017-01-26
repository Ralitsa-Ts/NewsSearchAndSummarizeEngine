package com.information.retrieval.indexer.util;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class BaseDocumentParserUtil {

    protected static final String PROPERTY_ATTRIBUTE_OG_TYPE_VALUE = "og:type";
    protected static final String PROPERTY_ATTRIBUTE = "property";
    protected static final String CONTENT_ATTRIBUTE = "content";

    public static String retrievePageType(Document document) {
        Element pageType = document.getElementsByAttributeValue(PROPERTY_ATTRIBUTE,
                        PROPERTY_ATTRIBUTE_OG_TYPE_VALUE).first();
        return pageType != null ? pageType.attr(CONTENT_ATTRIBUTE) : null;
    }

    public static String retrieveTitle(Document document) {
        return document.title();
    }
}
