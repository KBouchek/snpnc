package org.snpnc.snpnc;

/**
 * Created by K on 14/12/2017.
 */
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class RssReader {
    private String rssUrl;

    public RssReader(String rssUrl) {
        this.rssUrl = rssUrl;
    }


    public List<RssItem> getItems() throws Exception {

        // SAX parse RSS data
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();

        RssParseHandler handler = new RssParseHandler();

        saxParser.parse(rssUrl, handler);

        return handler.getItems();

    }
}
