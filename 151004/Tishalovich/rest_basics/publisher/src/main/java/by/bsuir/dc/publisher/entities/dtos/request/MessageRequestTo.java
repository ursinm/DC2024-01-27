package by.bsuir.dc.publisher.entities.dtos.request;

public record MessageRequestTo(
        Long id,
        Long storyId,
        String country,
        String content) {
}
