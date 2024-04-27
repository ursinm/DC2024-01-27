package by.bsuir.news.dto.request;

import by.bsuir.news.entity.Marker;

public class MarkerRequestTo {
    private Long id;
    private String name;
    public MarkerRequestTo() {

    }

    public static Marker fromRequest(MarkerRequestTo request) {
        Marker marker = new Marker();
        marker.setId(request.id);
        marker.setName(request.name);
        //
        return marker;
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
}
