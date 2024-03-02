package by.bsuir.taskrest.controller;

import by.bsuir.taskrest.dto.request.MessageRequestTo;
import by.bsuir.taskrest.dto.response.MessageResponseTo;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MessageControllerTest extends CrudControllerTest<MessageRequestTo, MessageResponseTo> {

    private final Random random = new Random();

    @Override
    protected MessageRequestTo getRandomRequestTo() {
        return new MessageRequestTo(
                null,
                random.nextLong(),
                "content" + random.nextInt()
        );
    }

    @Override
    protected MessageRequestTo getRandomRequestTo(Long id) {
        return new MessageRequestTo(
                id,
                random.nextLong(),
                "content" + random.nextInt()
        );
    }

    @Override
    protected String getPath() {
        return "/messages";
    }

    @Override
    protected void assertReqAndResEquals(MessageRequestTo request, MessageResponseTo response) {
        assertEquals(request.content(), response.content());
    }

    @Override
    protected Long getId(MessageResponseTo response) {
        return response.id();
    }
}
