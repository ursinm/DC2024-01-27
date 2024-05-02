package com.poluectov.rvproject.service.message;

import com.poluectov.rvproject.dto.message.MessageRequestTo;
import com.poluectov.rvproject.dto.message.MessageResponseTo;
import com.poluectov.rvproject.model.Message;
import com.poluectov.rvproject.repository.MessageRepository;
import com.poluectov.rvproject.repository.exception.EntityNotFoundException;
import com.poluectov.rvproject.service.IssueService;
import com.poluectov.rvproject.service.redis.RedisCacheService;
import com.poluectov.rvproject.utils.dtoconverter.MessageRequestDtoConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@Slf4j
public class CachedMessageService extends MessageService {

    RedisCacheService<Long, MessageResponseTo> redisCacheService;
    ExecutorService executorService;

    @Autowired
    public CachedMessageService(
            @Qualifier("kafkaMessageRepository") MessageRepository repository,
            MessageRequestDtoConverter messageRequestDtoConverter,
            RedisCacheService<Long, MessageResponseTo> redisCacheService,
            @Value("${redis.threads.count}") Integer threadsCount,
            IssueService issueService
    ) {
        super(repository, messageRequestDtoConverter, issueService);
        this.redisCacheService = redisCacheService;
        this.executorService = Executors.newFixedThreadPool(threadsCount);
    }

    @Override
    public Optional<MessageResponseTo> update(Long aLong, MessageRequestTo messageRequestTo) {

        if (redisCacheService.get(aLong) != null) {
            MessageResponseTo updated = convert(messageRequestTo);
            redisCacheService.put(aLong, updated);
            // update async
            executorService.execute(() -> {
                try {
                    super.update(aLong, messageRequestTo);
                }catch (Exception e) {
                    log.error("Error updating message", e);
                    redisCacheService.delete(aLong);
                }
            });

            return Optional.of(updated);
        }

        Optional<MessageResponseTo> messageResponseTo = super.update(aLong, messageRequestTo);
        messageResponseTo.ifPresent(responseTo -> redisCacheService.put(aLong, responseTo));
        return messageResponseTo;
    }

    @Override
    public void delete(Long aLong) throws EntityNotFoundException {
        redisCacheService.delete(aLong);
        super.delete(aLong);
    }

    @Override
    public Optional<MessageResponseTo> one(Long aLong) {
        MessageResponseTo messageResponseTo = redisCacheService.get(aLong);
        if (messageResponseTo != null) {
            return Optional.of(messageResponseTo);
        }

        Optional<MessageResponseTo> response = super.one(aLong);
        response.ifPresent(messageResponseTo1 -> redisCacheService.put(aLong, messageResponseTo1));
        return response;
    }

    @Override
    public Optional<MessageResponseTo> create(MessageRequestTo messageRequestTo) {
        Optional<MessageResponseTo> messageResponseTo = super.create(messageRequestTo);
        messageResponseTo.ifPresent(responseTo -> redisCacheService.put(responseTo.getId(), responseTo));

        return messageResponseTo;
    }

    private MessageResponseTo convert(MessageRequestTo messageRequestTo) {
        return MessageResponseTo.builder()
                .id(messageRequestTo.getId())
                .content(messageRequestTo.getContent())
                .issueId(messageRequestTo.getIssueId())
                .build();
    }
}
