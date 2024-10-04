package searchengine.dto.statistics;

import java.util.List;

public class StatisticsResponse {

    private boolean result;
    private StatisticsData statistics;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public StatisticsData getStatistics() {
        return statistics;
    }

    public void setStatistics(StatisticsData statistics) {
        this.statistics = statistics;
    }

    // Вложенный класс для данных статистики
    public static class StatisticsData {
        private int sites;
        private int pages;
        private int lemmas;
        private boolean isIndexing;
        private List<SiteStatistics> detailed;

        // Getters and Setters

        public int getSites() {
            return sites;
        }

        public void setSites(int sites) {
            this.sites = sites;
        }

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public int getLemmas() {
            return lemmas;
        }

        public void setLemmas(int lemmas) {
            this.lemmas = lemmas;
        }

        public boolean isIndexing() {
            return isIndexing;
        }

        public void setIndexing(boolean indexing) {
            isIndexing = indexing;
        }

        public List<SiteStatistics> getDetailed() {
            return detailed;
        }

        public void setDetailed(List<SiteStatistics> detailed) {
            this.detailed = detailed;
        }
    }
}
