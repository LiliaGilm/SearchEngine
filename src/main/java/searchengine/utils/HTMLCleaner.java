package searchengine.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

@Component
public class HTMLCleaner {

    public String clean(String html) {
        Document doc = Jsoup.parse(html);
        return doc.text();
    }
}
