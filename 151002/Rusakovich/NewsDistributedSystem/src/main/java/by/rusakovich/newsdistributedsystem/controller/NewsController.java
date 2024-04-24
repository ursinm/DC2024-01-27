package by.rusakovich.newsdistributedsystem.controller;

import by.rusakovich.newsdistributedsystem.model.dto.news.NewsRequestTO;
import by.rusakovich.newsdistributedsystem.model.dto.news.NewsResponseTO;
import by.rusakovich.newsdistributedsystem.service.impl.NewsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1.0/news")
public class NewsController extends Controller<Long, NewsRequestTO, NewsResponseTO, NewsService> {

    public NewsController(NewsService service) {
        super(service);
    }
}
