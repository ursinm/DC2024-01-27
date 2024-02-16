package by.bsuir.dc.rest_basics.services.validation;

import by.bsuir.dc.rest_basics.dtos.request.AuthorRequestTo;
import by.bsuir.dc.rest_basics.services.exceptions.ApiException;
import by.bsuir.dc.rest_basics.services.exceptions.ApiExceptionInfo;
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
    public Object validate(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        AuthorRequestTo authorRequestTo = (AuthorRequestTo) args[0];

        int loginLen = authorRequestTo.getLogin().length();
        checkLoginLen(loginLen);

        int passwordLen = authorRequestTo.getPassword().length();
        checkPasswordLen(passwordLen);

        int firstNameLen = authorRequestTo.getFirstName().length();
        checkFirstNameLen(firstNameLen);

        int lastNameLen = authorRequestTo.getLastName().length();
        checkLastNameLen(lastNameLen);

        return joinPoint.proceed();
    }

    private void checkLoginLen(int loginLen) throws ApiException {
        if (!(2 <= loginLen) || !(loginLen <= 64)) {
            throw new ApiException(
                    HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    AuthorSubCode.WRONG_LOGIN_LEN.getSubCode(),
                    "Author's login len must be from 2 to 64"
            );
        }
    }

    private void checkPasswordLen(int passwordLen) throws ApiException {
        if (!(8 <= passwordLen) || !(passwordLen <= 128)) {
            throw new ApiException(
                    HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    AuthorSubCode.WRONG_PASSWORD_LEN.getSubCode(),
                    "Author's password len must be from 8 to 128"
            );
        }
    }
    private void checkFirstNameLen(int firstNameLen) throws ApiException {
        if (!(2 <= firstNameLen) || !(firstNameLen <= 64)) {
            throw new ApiException(
                    HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    AuthorSubCode.WRONG_FIRST_NAME_LEN.getSubCode(),
                    "Author's first name len must be from 2 to 64"
            );
        }
    }

    private void checkLastNameLen(int lastNameLen) throws ApiException {
        if (!(2 <= lastNameLen) || !(lastNameLen <= 64)) {
            throw new ApiException(
                    HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    AuthorSubCode.WRONG_LAST_NAME_LEN.getSubCode(),
                    "Author's last name len must be from 2 to 64"
            );
        }
    }

}
