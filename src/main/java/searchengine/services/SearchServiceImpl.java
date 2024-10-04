package searchengine.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.model.Index;
import searchengine.model.Lemma;
import searchengine.model.Page;
import searchengine.model.SearchResult;
import searchengine.repositories.IndexRepository;
import searchengine.repositories.LemmaRepository;
import searchengine.repositories.PageRepository;
import searchengine.utils.Lemmatizer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private LemmaRepository lemmaRepository;

    @Autowired
    private IndexRepository indexRepository;

    @Autowired
    private PageRepository pageRepository;

    @Autowired
    private Lemmatizer lemmatizer;

    @Override
    public List<SearchResult> search(String query) {
        Map<String, Integer> lemmas = lemmatizer.lemmatize(query);
        List<Page> pages = findRelevantPages(lemmas);
        return calculateRelevance(pages, lemmas);
    }

    private List<Page> findRelevantPages(Map<String, Integer> lemmas) {
        List<Page> pages = new ArrayList<>();
        for (String lemma : lemmas.keySet()) {
            Lemma lemmaObj = lemmaRepository.findByLemma(lemma);
            if (lemmaObj != null) {
                List<Index> indices = indexRepository.findAllByLemma(lemmaObj);
                for (Index index : indices) {
                    pages.add(index.getPage());
                }
            }
        }
        return pages;
    }

    private List<SearchResult> calculateRelevance(List<Page> pages, Map<String, Integer> lemmas) {
        List<SearchResult> results = new ArrayList<>();
        for (Page page : pages) {
            float relevance = 0.0f;
            for (String lemma : lemmas.keySet()) {
                Lemma lemmaObj = lemmaRepository.findByLemma(lemma);
                if (lemmaObj != null) {
                    Index index = indexRepository.findByPageAndLemma(page, lemmaObj);
                    if (index != null) {
                        relevance += index.getRank();
                    }
                }
            }
            results.add(new SearchResult(page.getPath(), page.getContent(), relevance));
        }
        results.sort((r1, r2) -> Float.compare(r2.getRelevance(), r1.getRelevance()));
        return results;
    }
}
