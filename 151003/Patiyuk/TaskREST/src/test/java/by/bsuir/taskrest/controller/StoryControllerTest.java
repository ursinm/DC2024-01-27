package by.bsuir.taskrest.controller;

import by.bsuir.taskrest.dto.request.StoryRequestTo;
import by.bsuir.taskrest.dto.response.StoryResponseTo;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StoryControllerTest extends CrudControllerTest<StoryRequestTo, StoryResponseTo> {

    private final Random random = new Random();

    @Override
    protected StoryRequestTo getRandomRequestTo() {
        return new StoryRequestTo(
                null,
                random.nextLong(),
                "title" + random.nextInt(),
                "content" + random.nextInt()
        );
    }

    @Override
    protected StoryRequestTo getRandomRequestTo(Long id) {
        return new StoryRequestTo(
                id,
                random.nextLong(),
                "title" + random.nextInt(),
                "content" + random.nextInt()
        );
    }

    @Override
    protected String getPath() {
        return "/storys";
    }

    @Override
    protected void assertReqAndResEquals(StoryRequestTo request, StoryResponseTo response) {
        assertEquals(request.title(), response.title());
        assertEquals(request.content(), response.content());
    }

    @Override
    protected Long getId(StoryResponseTo response) {
        return response.id();
    }
}
