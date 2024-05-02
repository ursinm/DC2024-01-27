package com.luschickij.publisher.controller.crud;

import com.luschickij.publisher.dto.news.NewsRequestTo;
import com.luschickij.publisher.dto.news.NewsResponseTo;
import com.luschickij.publisher.model.News;
import com.luschickij.publisher.service.CommonRestService;
import com.luschickij.publisher.service.NewsService;
import com.luschickij.publisher.utils.modelassembler.NewsModelAssembler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/news")
public class NewsController extends CommonRESTController<News, NewsRequestTo, NewsResponseTo> {
    public NewsController(NewsService service,
                           NewsModelAssembler assembler) {
        super(service, assembler::toModel);
    }
}