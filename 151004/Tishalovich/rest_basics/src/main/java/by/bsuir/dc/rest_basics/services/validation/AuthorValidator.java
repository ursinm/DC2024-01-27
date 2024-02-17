package by.bsuir.dc.rest_basics.services.validation;

import by.bsuir.dc.rest_basics.entities.dtos.request.AuthorRequestTo;
import by.bsuir.dc.rest_basics.services.exceptions.ApiException;
import by.bsuir.dc.rest_basics.services.exceptions.AuthorSubCode;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuthorValidator {

    @Around("execution(* by.bsuir.dc.rest_basics.services.AuthorService.create(..))")
    public Object validateCreate(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        AuthorRequestTo authorRequestTo = (AuthorRequestTo) args[0];

        String login = authorRequestTo.getLogin();
        if (login == null) {
            throw new ApiException(
                    HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    AuthorSubCode.NO_LOGIN_PROVIDED.getSubCode(),
                    "No login provided"
            );
        }
        checkLogin(authorRequestTo.getLogin());

        String password = authorRequestTo.getPassword();
        if (password == null) {
            throw new ApiException(
                    HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    AuthorSubCode.NO_PASSWORD_PROVIDED.getSubCode(),
                    "No password provided"
            );
        }
        checkPassword(authorRequestTo.getPassword());

        String firstName = authorRequestTo.getFirstName();
        if (firstName == null) {
            throw new ApiException(
                    HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    AuthorSubCode.NO_FIRST_NAME_PROVIDED.getSubCode(),
                    "No firstName provided"
            );
        }
        checkFirstName(authorRequestTo.getFirstName());

        String lastName = authorRequestTo.getLastName();
        if (lastName == null) {
            throw new ApiException(
                    HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    AuthorSubCode.NO_LAST_NAME_PROVIDED.getSubCode(),
                    "No lastName provided"
            );
        }
        checkLastName(authorRequestTo.getLastName());

        return joinPoint.proceed();
    }

    private void checkLogin(String login) throws ApiException {
        int loginLen = login.length();

        if (!(2 <= loginLen) || !(loginLen <= 64)) {
            throw new ApiException(
                    HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    AuthorSubCode.WRONG_LOGIN_LEN.getSubCode(),
                    "Author's login len must be from 2 to 64"
            );
        }
    }

    private void checkPassword(String password) throws ApiException {
        int passwordLen = password.length();

        if (!(8 <= passwordLen) || !(passwordLen <= 128)) {
            throw new ApiException(
                    HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    AuthorSubCode.WRONG_PASSWORD_LEN.getSubCode(),
                    "Author's password len must be from 8 to 128"
            );
        }
    }
    private void checkFirstName(String firstName) throws ApiException {
        int firstNameLen = firstName.length();

        if (!(2 <= firstNameLen) || !(firstNameLen <= 64)) {
            throw new ApiException(
                    HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    AuthorSubCode.WRONG_FIRST_NAME_LEN.getSubCode(),
                    "Author's first name len must be from 2 to 64"
            );
        }
    }

    private void checkLastName(String lastName) throws ApiException {
        int lastNameLen = lastName.length();

        if (!(2 <= lastNameLen) || !(lastNameLen <= 64)) {
            throw new ApiException(
                    HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    AuthorSubCode.WRONG_LAST_NAME_LEN.getSubCode(),
                    "Author's last name len must be from 2 to 64"
            );
        }
    }

    @Around("execution(* by.bsuir.dc.rest_basics.services.AuthorService.update(..))")
    public Object validateUpdate(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        AuthorRequestTo authorRequestTo = (AuthorRequestTo) args[0];

        String login = authorRequestTo.getLogin();
        if (login != null) {
            checkLogin(authorRequestTo.getLogin());
        }

        String password = authorRequestTo.getPassword();
        if (password != null) {
            checkPassword(authorRequestTo.getPassword());
        }

        String firstName = authorRequestTo.getFirstName();
        if (firstName != null) {
            checkFirstName(authorRequestTo.getFirstName());
        }

        String lastName = authorRequestTo.getLastName();
        if (lastName != null) {
            checkLastName(authorRequestTo.getLastName());
        }

        return joinPoint.proceed();
    }

}
