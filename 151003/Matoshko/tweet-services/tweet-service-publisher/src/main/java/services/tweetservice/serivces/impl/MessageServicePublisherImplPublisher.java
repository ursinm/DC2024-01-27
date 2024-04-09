package services.tweetservice.serivces.impl;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import services.tweetservice.domain.entity.ValidationMarker;
import services.tweetservice.domain.request.MessageRequestTo;
import services.tweetservice.domain.response.MessageResponseTo;
import services.tweetservice.exceptions.NoSuchMessageException;
import services.tweetservice.exceptions.NoSuchTweetException;
import services.tweetservice.serivces.MessageServicePublisher;
import services.tweetservice.serivces.TweetService;

import java.util.List;

@Service
@Transactional
@Validated
public class MessageServicePublisherImplPublisher implements MessageServicePublisher {
    private final TweetService tweetService;
    private final RestTemplate restTemplate;

    @Value("${discussion.service.uri}")
    private String baseDiscussionServiceUri;

    @Autowired
    public MessageServicePublisherImplPublisher(TweetService tweetService) {
        this.tweetService = tweetService;
        this.restTemplate = new RestTemplate();
    }

    @Override
    @Validated(ValidationMarker.OnCreate.class)
    public MessageResponseTo create(@Valid MessageRequestTo entity) {
        return restTemplate.postForObject(baseDiscussionServiceUri + MessageOperationUri.CREATE_MESSAGE_URI.getValue(), entity, MessageResponseTo.class);
    }

    @Override
    public List<MessageResponseTo> read() {
        return restTemplate.exchange(baseDiscussionServiceUri + MessageOperationUri.READ_MESSAGES_URI.getValue(),
                HttpMethod.GET, null, new ParameterizedTypeReference<List<MessageResponseTo>>() {
                }).getBody();
    }

    @Override
    @Validated(ValidationMarker.OnUpdate.class)
    public MessageResponseTo update(@Valid MessageRequestTo entity) {
        if (tweetService.existsById(entity.tweetId())) {
            try {
                return restTemplate.exchange(baseDiscussionServiceUri + MessageOperationUri.UPDATE_MESSAGE_URI.getValue(),
                        HttpMethod.PUT, new HttpEntity<>(entity), MessageResponseTo.class).getBody();
            } catch (HttpClientErrorException e) {
                throw new NoSuchMessageException(entity.id());
            }
        } else {
            throw new NoSuchTweetException(entity.tweetId());
        }
    }

    @Override
    public Long delete(Long id) {
        try {
            restTemplate.delete(baseDiscussionServiceUri + MessageOperationUri.DELETE_MESSAGE_URI.getValue() + id);
            return id;
        } catch (HttpClientErrorException e) {
            throw new NoSuchMessageException(id);
        }
    }

    @Override
    public MessageResponseTo findMessageById(Long id) {
        try {
            return restTemplate.getForObject(baseDiscussionServiceUri + MessageOperationUri.FIND_MESSAGE_BY_ID_URI.getValue() + id,
                    MessageResponseTo.class);
        } catch (HttpClientErrorException e) {
            throw new NoSuchMessageException(id);
        }


    }
}
