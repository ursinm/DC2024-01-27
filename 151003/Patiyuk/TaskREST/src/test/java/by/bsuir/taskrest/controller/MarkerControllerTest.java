package by.bsuir.taskrest.controller;

import by.bsuir.taskrest.dto.request.MarkerRequestTo;
import by.bsuir.taskrest.dto.response.MarkerResponseTo;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MarkerControllerTest extends CrudControllerTest<MarkerRequestTo, MarkerResponseTo> {

    private final Random random = new Random();

    @Override
    protected MarkerRequestTo getRandomRequestTo() {
        return new MarkerRequestTo(
                null,
                "name" + random.nextInt()
        );
    }

    @Override
    protected MarkerRequestTo getRandomRequestTo(Long id) {
        return new MarkerRequestTo(
                id,
                "name" + random.nextInt()
        );
    }

    @Override
    protected String getPath() {
        return "/markers";
    }

    @Override
    protected void assertReqAndResEquals(MarkerRequestTo request, MarkerResponseTo response) {
        assertEquals(request.name(), response.name());
    }

    @Override
    protected Long getId(MarkerResponseTo response) {
        return response.id();
    }
}
