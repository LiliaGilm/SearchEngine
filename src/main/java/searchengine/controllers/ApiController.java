package searchengine.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import searchengine.services.IndexingService;
import searchengine.services.SearchService;
import searchengine.model.SearchResult;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private IndexingService indexingService;

    @Autowired
    private SearchService searchService;

    @GetMapping("/startIndexing")
    public ResponseEntity<?> startIndexing() {
        indexingService.startIndexing();
        return ResponseEntity.ok("Indexing started");
    }

    @GetMapping("/stopIndexing")
    public ResponseEntity<?> stopIndexing() {
        indexingService.stopIndexing();
        return ResponseEntity.ok("Indexing stopped");
    }

    @GetMapping("/search")
    public ResponseEntity<List<SearchResult>> search(@RequestParam String query) {
        List<SearchResult> results = searchService.search(query);
        return ResponseEntity.ok(results);
    }
}
