package by.bsuir.dc.publisher.services.exceptions;

import lombok.Getter;

@Getter
public enum StorySubCode {

    //422 - Unprocessable entity
    WRONG_TITLE_LEN(1, "Story title length must be from 2 to 64"),
    WRONG_CONTENT_LEN(2, "Story content length must be from 4 to 2048"),
    AUTHOR_ID_IS_NULL(3, "Author id must be not null"),
    TITLE_IS_NULL(4, "Title must be not null"),
    CONTENT_IS_NULL(5, "Content must be not null"),
    CREATED_IS_NULL(6, "Created must be not null"),
    MODIFIED_IS_NULL(7, "Modified must be not null");

    private final int subCode;

    private final String message;

    StorySubCode(int subCode, String message) {
        this.subCode = subCode;
        this.message = message;
    }

}
