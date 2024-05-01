package by.bsuir.news.dto.response;

import by.bsuir.news.entity.Marker;

import java.util.List;
import java.util.stream.Collectors;

public class MarkerResponseTo implements IResponseTO<Marker, MarkerResponseTo>{
    private Long id;
    private String name;
    private List<NewsResponseTo> news;
    @Override
    public MarkerResponseTo toModel(Marker source) {
        MarkerResponseTo model = new MarkerResponseTo();
        model.id = source.getId();
        model.name = source.getName();
        return model;
    }

    public static MarkerResponseTo toResponse(Marker source) {
        MarkerResponseTo model = new MarkerResponseTo();
        model.id = source.getId();
        model.name = source.getName();
        //model.news = source.getNews().stream().map(NewsResponseTo::toResponse).collect(Collectors.toList());
        return model;
    }

    public MarkerResponseTo() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<NewsResponseTo> getNews() {
        return news;
    }

    public void setNews(List<NewsResponseTo> news) {
        this.news = news;
    }

}
