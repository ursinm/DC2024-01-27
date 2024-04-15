package by.bsuir.dc.publisher.services.validation;

import by.bsuir.dc.publisher.services.exceptions.ApiException;
import by.bsuir.dc.publisher.entities.dtos.request.MessageRequestTo;
import by.bsuir.dc.publisher.services.exceptions.GeneralSubCode;
import by.bsuir.dc.publisher.services.exceptions.MessageSubCode;
import org.springframework.http.HttpStatus;

public class MessageValidator implements EntityValidator {

    private void checkContent(String content) throws ApiException {
        int contentLen = content.length();

        if ((contentLen < 4) || (contentLen > 2048)) {
            throw new ApiException(
                    HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    MessageSubCode.WRONG_CONTENT_LEN.getSubCode(),
                    MessageSubCode.WRONG_CONTENT_LEN.getMessage()
            );
        }
    }

    @Override
    public void validateCreate(Object o) throws ApiException {
        MessageRequestTo messageRequestTo = (MessageRequestTo) o;

        String content = messageRequestTo.content();
        if (content == null) {
            throw new ApiException(
                    HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    MessageSubCode.CONTENT_IS_NULL.getSubCode(),
                    MessageSubCode.CONTENT_IS_NULL.getMessage()
            );
        }

        checkContent(content);

        Long storyId = messageRequestTo.storyId();
        if (storyId == null) {
            throw new ApiException(
                    HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    MessageSubCode.STORY_ID_IS_NULL.getSubCode(),
                    MessageSubCode.STORY_ID_IS_NULL.getMessage()
            );
        }
    }

    @Override
    public void validateUpdate(Object o) throws ApiException {
        MessageRequestTo messageRequestTo = (MessageRequestTo) o;

        if (messageRequestTo.id() == null) {
            throw new ApiException(
                    HttpStatus.NOT_FOUND.value(),
                    GeneralSubCode.WRONG_ID.getSubCode(),
                    GeneralSubCode.WRONG_ID.getMessage()
            );
        }

        String content = messageRequestTo.content();
        if (content != null) {
            checkContent(content);
        }
    }

}
