package by.bsuir.news.dto.response;

public interface IResponseTO<T, R> {
    public R toModel(T source);
}
