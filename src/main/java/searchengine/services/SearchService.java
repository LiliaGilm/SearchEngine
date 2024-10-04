package searchengine.services;

import searchengine.model.Page;
import searchengine.model.SearchResult;

import java.util.List;

public interface SearchService {
    List<SearchResult> search(String query);
}
