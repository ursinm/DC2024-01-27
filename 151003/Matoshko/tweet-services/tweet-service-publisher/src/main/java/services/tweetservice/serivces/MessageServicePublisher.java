package services.tweetservice.serivces;

import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import services.tweetservice.domain.entity.ValidationMarker;
import services.tweetservice.domain.request.MessageRequestTo;
import services.tweetservice.domain.response.MessageResponseTo;
import services.tweetservice.exceptions.ServiceException;

import java.util.List;
import java.util.Optional;

public interface MessageServicePublisher {
    @Validated(ValidationMarker.OnCreate.class)
    MessageResponseTo create(@Valid MessageRequestTo entity) throws ServiceException;

    List<MessageResponseTo> read();

    @Validated(ValidationMarker.OnUpdate.class)
    MessageResponseTo update(@Valid MessageRequestTo entity) throws ServiceException;

    Long delete(Long id) throws ServiceException;

    Optional<MessageResponseTo> findMessageById(Long id);
}
