package by.bsuir.taskrest.controller;

import by.bsuir.taskrest.dto.request.CreatorRequestTo;
import by.bsuir.taskrest.dto.response.CreatorResponseTo;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CreatorControllerTest extends CrudControllerTest<CreatorRequestTo, CreatorResponseTo> {

    private final Random random = new Random();

    @Override
    protected CreatorRequestTo getRandomRequestTo() {
        return new CreatorRequestTo(
                null,
                "login" + random.nextInt(),
                "password" + random.nextInt(),
                "firstname" + random.nextInt(),
                "lastname" + random.nextInt()
        );
    }

    @Override
    protected CreatorRequestTo getRandomRequestTo(Long id) {
        return new CreatorRequestTo(
                id,
                "login" + random.nextInt(),
                "password" + random.nextInt(),
                "firstname" + random.nextInt(),
                "lastname" + random.nextInt()
        );
    }

    @Override
    protected String getPath() {
        return "/creators";
    }

    @Override
    protected void assertReqAndResEquals(CreatorRequestTo request, CreatorResponseTo response) {
        assertEquals(request.login(), response.login());
        assertEquals(request.firstname(), response.firstname());
        assertEquals(request.lastname(), response.lastname());
    }

    @Override
    protected Long getId(CreatorResponseTo response) {
        return response.id();
    }
}