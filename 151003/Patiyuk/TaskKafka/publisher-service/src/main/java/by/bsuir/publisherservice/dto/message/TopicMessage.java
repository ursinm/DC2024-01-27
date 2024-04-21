package by.bsuir.publisherservice.dto.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
public abstract class TopicMessage {
    @Getter
    @AllArgsConstructor
    @ToString
    public enum Operation {
        CREATE("create"),
        GET_ALL("get_all"),
        GET_BY_STORY_ID("get_by_story_id"),
        GET_BY_ID("get_by_id"),
        UPDATE("update"),
        DELETE("delete");

        private final String operationName;
    }

    private Operation operation;
    private String error;

    public boolean isSuccessful() {
        return error == null;
    }
}
