package by.bsuir.dc.publisher.services.validation;

import by.bsuir.dc.publisher.services.exceptions.ApiException;
import by.bsuir.dc.publisher.services.exceptions.GeneralSubCode;
import by.bsuir.dc.publisher.entities.dtos.request.LabelRequestTo;
import by.bsuir.dc.publisher.services.exceptions.LabelSubCode;
import org.springframework.http.HttpStatus;

public class LabelValidator implements EntityValidator {

    private void validateName(String name) throws ApiException {
        if (name == null) {
            throw new ApiException(
                    HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    LabelSubCode.NO_NAME_PROVIDED.getSubCode(),
                    LabelSubCode.NO_NAME_PROVIDED.getMessage()
            );
        }

        if (name.length() < 2 || name.length() > 32) {
            throw new ApiException(
                    HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    LabelSubCode.WRONG_NAME_LEN.getSubCode(),
                    LabelSubCode.WRONG_NAME_LEN.getMessage()
            );
        }
    }

    @Override
    public void validateCreate(Object o) throws ApiException {
        LabelRequestTo labelRequestTo = (LabelRequestTo) o;

        validateName(labelRequestTo.name());
    }

    @Override
    public void validateUpdate(Object o) throws ApiException {
        LabelRequestTo labelRequestTo = (LabelRequestTo) o;

        if (labelRequestTo.id() == null) {
            throw new ApiException(
                    HttpStatus.NOT_FOUND.value(),
                    GeneralSubCode.WRONG_ID.getSubCode(),
                    GeneralSubCode.WRONG_ID.getMessage()
            );
        }

        validateName(labelRequestTo.name());
    }

}
