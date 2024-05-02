package by.bsuir.news.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_news_marker")
public class NewsMarker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private News news;
    @ManyToOne
    private Marker marker;


    public NewsMarker() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }

    public Marker getMarker() {
        return marker;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }
}
