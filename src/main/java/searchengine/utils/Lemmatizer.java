package searchengine.utils;

import org.apache.lucene.morphology.LuceneMorphology;
import org.apache.lucene.morphology.russian.RussianLuceneMorphology;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Lemmatizer {

    private LuceneMorphology morphology;

    public Lemmatizer() throws IOException {
        morphology = new RussianLuceneMorphology();
    }

    public Map<String, Integer> lemmatize(String text) {
        Map<String, Integer> lemmas = new HashMap<>();
        String[] words = text.split("\\W+");
        for (String word : words) {
            if (!word.isEmpty()) {
                List<String> baseForms = morphology.getNormalForms(word.toLowerCase());
                for (String baseForm : baseForms) {
                    lemmas.put(baseForm, lemmas.getOrDefault(baseForm, 0) + 1);
                }
            }
        }
        return lemmas;
    }
}
