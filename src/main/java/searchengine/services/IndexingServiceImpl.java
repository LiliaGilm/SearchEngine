package searchengine.services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.model.Page;
import searchengine.model.Site;
import searchengine.repositories.PageRepository;
import searchengine.repositories.SiteRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class IndexingServiceImpl implements IndexingService {

    @Autowired
    private SiteRepository siteRepository;

    @Autowired
    private PageRepository pageRepository;

    @Override
    public void startIndexing() {
        List<Site> sites = siteRepository.findAll();
        for (Site site : sites) {
            indexSite(site);
        }
    }

    private void indexSite(Site site) {
        site.setStatus("INDEXING");
        site.setStatusTime(LocalDateTime.now());
        siteRepository.save(site);
        
        try {
            Document doc = Jsoup.connect(site.getUrl()).get();
            indexPage(site, "/", doc);
            Elements links = doc.select("a[href]");
            for (Element link : links) {
                String url = link.attr("href");
                if (!url.startsWith("http")) {
                    url = site.getUrl() + url;
                }
                if (pageRepository.findByPath(url) == null) {
                    Document linkedDoc = Jsoup.connect(url).get();
                    indexPage(site, url, linkedDoc);
                }
            }
            site.setStatus("INDEXED");
        } catch (IOException e) {
            site.setStatus("FAILED");
            site.setLastError(e.getMessage());
        } finally {
            site.setStatusTime(LocalDateTime.now());
            siteRepository.save(site);
        }
    }

    private void indexPage(Site site, String url, Document doc) {
        Page page = new Page();
        page.setSite(site);
        page.setPath(url);
        page.setCode(200); // Предполагаем, что всегда успешно
        page.setContent(doc.html());
        pageRepository.save(page);
    }

    @Override
    public void stopIndexing() {
        // Код для остановки индексации
    }
}
