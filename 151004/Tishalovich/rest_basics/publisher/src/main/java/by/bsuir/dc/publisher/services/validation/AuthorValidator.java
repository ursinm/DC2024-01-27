package by.bsuir.dc.publisher.services.validation;

import by.bsuir.dc.publisher.entities.dtos.request.AuthorRequestTo;
import by.bsuir.dc.publisher.services.exceptions.ApiException;
import by.bsuir.dc.publisher.services.exceptions.AuthorSubCode;
import by.bsuir.dc.publisher.services.exceptions.GeneralSubCode;
import org.springframework.http.HttpStatus;

public class AuthorValidator implements EntityValidator {

    private void checkLogin(String login) throws ApiException {
        int loginLen = login.length();

        if (!(2 <= loginLen) || !(loginLen <= 64)) {
            throw new ApiException(
                    HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    AuthorSubCode.WRONG_LOGIN_LEN.getSubCode(),
                    AuthorSubCode.WRONG_LOGIN_LEN.getMessage()
            );
        }
    }

    private void checkPassword(String password) throws ApiException {
        int passwordLen = password.length();

        if (!(8 <= passwordLen) || !(passwordLen <= 128)) {
            throw new ApiException(
                    HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    AuthorSubCode.WRONG_PASSWORD_LEN.getSubCode(),
                    AuthorSubCode.WRONG_PASSWORD_LEN.getMessage()
            );
        }
    }

    private void checkFirstName(String firstname) throws ApiException {
        int firstnameLen = firstname.length();

        if (!(2 <= firstnameLen) || !(firstnameLen <= 64)) {
            throw new ApiException(
                    HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    AuthorSubCode.WRONG_FIRST_NAME_LEN.getSubCode(),
                    AuthorSubCode.WRONG_FIRST_NAME_LEN.getMessage()
            );
        }
    }

    private void checkLastName(String lastname) throws ApiException {
        int lastnameLen = lastname.length();

        if (!(2 <= lastnameLen) || !(lastnameLen <= 64)) {
            throw new ApiException(
                    HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    AuthorSubCode.WRONG_LAST_NAME_LEN.getSubCode(),
                    AuthorSubCode.WRONG_LAST_NAME_LEN.getMessage()
            );
        }
    }

    @Override
    public void validateCreate(Object o) throws ApiException {
        AuthorRequestTo authorRequestTo = (AuthorRequestTo) o;

        String login = authorRequestTo.login();
        if (login == null) {
            throw new ApiException(
                    HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    AuthorSubCode.NO_LOGIN_PROVIDED.getSubCode(),
                    "No login provided"
            );
        }
        checkLogin(authorRequestTo.login());

        String password = authorRequestTo.password();
        if (password == null) {
            throw new ApiException(
                    HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    AuthorSubCode.NO_PASSWORD_PROVIDED.getSubCode(),
                    "No password provided"
            );
        }
        checkPassword(authorRequestTo.password());

        String firstname = authorRequestTo.firstname();
        if (firstname == null) {
            throw new ApiException(
                    HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    AuthorSubCode.NO_FIRST_NAME_PROVIDED.getSubCode(),
                    "No firstname provided"
            );
        }
        checkFirstName(authorRequestTo.firstname());

        String lastname = authorRequestTo.lastname();
        if (lastname == null) {
            throw new ApiException(
                    HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    AuthorSubCode.NO_LAST_NAME_PROVIDED.getSubCode(),
                    "No lastname provided"
            );
        }
        checkLastName(authorRequestTo.lastname());
    }

    @Override
    public void validateUpdate(Object o) throws ApiException {
        AuthorRequestTo authorRequestTo = (AuthorRequestTo) o;

        if (authorRequestTo.id() == null) {
            throw new ApiException(
                    HttpStatus.NOT_FOUND.value(),
                    GeneralSubCode.WRONG_ID.getSubCode(),
                    GeneralSubCode.WRONG_ID.getMessage()
            );
        }

        String login = authorRequestTo.login();
        if (login != null) {
            checkLogin(login);
        }

        String password = authorRequestTo.password();
        if (password != null) {
            checkPassword(password);
        }

        String firstname = authorRequestTo.firstname();
        if (firstname != null) {
            checkFirstName(firstname);
        }

        String lastname = authorRequestTo.lastname();
        if (lastname != null) {
            checkLastName(lastname);
        }
    }

}
