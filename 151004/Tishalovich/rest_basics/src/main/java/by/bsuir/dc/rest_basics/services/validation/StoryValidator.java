package by.bsuir.dc.rest_basics.services.validation;

import by.bsuir.dc.rest_basics.entities.dtos.request.StoryRequestTo;
import by.bsuir.dc.rest_basics.services.exceptions.ApiException;
import by.bsuir.dc.rest_basics.services.exceptions.GeneralSubCode;
import by.bsuir.dc.rest_basics.services.exceptions.StorySubCode;
import org.springframework.http.HttpStatus;

public class StoryValidator implements EntityValidator {

    private void checkTitle(String title) throws ApiException {
        int titleLen = title.length();

        if (titleLen < 2 || titleLen > 64) {
            throw new ApiException(
                    HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    StorySubCode.WRONG_CONTENT_LEN.getSubCode(),
                    StorySubCode.WRONG_CONTENT_LEN.getMessage()
            );
        }
    }

    private void checkContent(String content) throws ApiException {
        int contentLen = content.length();

        if ((contentLen < 4) || (contentLen > 2048)) {
            throw new ApiException(
                    HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    StorySubCode.WRONG_CONTENT_LEN.getSubCode(),
                    StorySubCode.WRONG_CONTENT_LEN.getMessage()
            );
        }
    }

    @Override
    public void validateCreate(Object o) throws ApiException {
        StoryRequestTo storyRequestTo = (StoryRequestTo) o;

        if (storyRequestTo.authorId() == null) {
            throw new ApiException(
                    HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    StorySubCode.AUTHOR_ID_IS_NULL.getSubCode(),
                    StorySubCode.AUTHOR_ID_IS_NULL.getMessage()
            );
        }

        String title = storyRequestTo.title();
        if (title == null) {
            throw new ApiException(
                    HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    StorySubCode.TITLE_IS_NULL.getSubCode(),
                    StorySubCode.TITLE_IS_NULL.getMessage()
            );
        }

        checkTitle(title);

        String content = storyRequestTo.content();
        if (content == null) {
            throw new ApiException(
                    HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    StorySubCode.CONTENT_IS_NULL.getSubCode(),
                    StorySubCode.CONTENT_IS_NULL.getMessage()
            );
        }

        checkContent(content);

        if (storyRequestTo.created() == null) {
            throw new ApiException(
                    HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    StorySubCode.CREATED_IS_NULL.getSubCode(),
                    StorySubCode.CREATED_IS_NULL.getMessage()
            );
        }

        if (storyRequestTo.modified() == null) {
            throw new ApiException(
                    HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    StorySubCode.MODIFIED_IS_NULL.getSubCode(),
                    StorySubCode.MODIFIED_IS_NULL.getMessage()
            );
        }
    }

    @Override
    public void validateUpdate(Object o) throws ApiException {
        StoryRequestTo storyRequestTo = (StoryRequestTo) o;

        if (storyRequestTo.id() == null) {
            throw new ApiException(
                    HttpStatus.NOT_FOUND.value(),
                    GeneralSubCode.WRONG_ID.getSubCode(),
                    GeneralSubCode.WRONG_ID.getMessage()
            );
        }

        String title = storyRequestTo.title();
        if (title != null) {
            checkTitle(title);
        }

        String content = storyRequestTo.content();
        if (content != null) {
            checkContent(content);
        }
    }

}
