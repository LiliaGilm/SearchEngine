package searchengine.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import searchengine.dto.statistics.StatisticsResponse;
import searchengine.services.StatisticsService;

@Controller
public class DefaultController {

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping("/")
    public String index(Model model) {
        StatisticsResponse stats = statisticsService.getStatistics();
        model.addAttribute("statistics", stats);
        return "index"; // возвращает шаблон "index.html"
    }
}
