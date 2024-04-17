package by.rusakovich.publisher.controller;

import by.rusakovich.publisher.model.dto.news.NewsRequestTO;
import by.rusakovich.publisher.model.dto.news.NewsResponseTO;
import by.rusakovich.publisher.service.impl.NewsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.url}/news")
public class NewsController extends Controller<Long, NewsRequestTO, NewsResponseTO, NewsService> {

    public NewsController(NewsService service) {
        super(service);
    }
}
