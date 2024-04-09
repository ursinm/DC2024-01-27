package services.tweetservice.serivces;

import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import services.tweetservice.domain.entity.ValidationMarker;
import services.tweetservice.domain.request.MessageRequestTo;
import services.tweetservice.domain.response.MessageResponseTo;

import java.util.List;

public interface MessageServicePublisher {
    @Validated(ValidationMarker.OnCreate.class)
    MessageResponseTo create(@Valid MessageRequestTo entity);

    List<MessageResponseTo> read();

    @Validated(ValidationMarker.OnUpdate.class)
    MessageResponseTo update(@Valid MessageRequestTo entity);

    Long delete(Long id);

    MessageResponseTo findMessageById(Long id);
}
