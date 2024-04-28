package by.bsuir.discussion.service;

import by.bsuir.discussion.model.response.CommentResponseTo;
import by.bsuir.discussion.event.*;
import by.bsuir.discussion.model.entity.Comment;
import by.bsuir.discussion.model.entity.CommentState;
import by.bsuir.discussion.dao.CommentRepository;
import by.bsuir.discussion.service.mapper.CommentKafkaMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaCommentServer {
    public static final String IN_TOPIC = "in-topic";
    public static final String OUT_TOPIC = "out-topic";
    private final CommentKafkaMapper commentKafkaMapper;
    private final CommentService commentService;
    private final CommentRepository commentRepository;
    private final KafkaTemplate<String, OutTopicEvent> kafkaTemplate;

    @KafkaListener(topics = IN_TOPIC, groupId = "group-id=#{T(java.util.UUID).randomUUID().toString()}")
    private void process(ConsumerRecord<String, InTopicEvent> record) {
        final String key = record.key();
        final InTopicEvent inTopicEvent = record.value();
        final InTopicMessage inTopicMsg = inTopicEvent.message();
        final CommentInTopicTo inTopicComment = inTopicMsg.commentDto();
        log.info(IN_TOPIC + ": key = " + key + "; operation = " + inTopicMsg.operation() + "; data = " + inTopicMsg.commentDto());
        List<CommentOutTopicTo> resultList = switch (inTopicMsg.operation()) {
            case GET_ALL -> commentKafkaMapper.responseDtoToOutTopicDto(commentService.findAll());
            case GET_BY_ID -> getById(inTopicComment.id());
            case DELETE_BY_ID -> delete(inTopicComment.id());
            case CREATE -> create(inTopicComment);
            case UPDATE -> update(inTopicComment);
        };
        log.info(OUT_TOPIC + ": key = " + key + "; data = " + resultList);
        kafkaTemplate.send(
                OUT_TOPIC,
                key,
                new OutTopicEvent(
                        inTopicEvent.id(),
                        new OutTopicMessage(
                                resultList
                        )
                )
        );
    }

    private List<CommentOutTopicTo> getById(Long id) {
        CommentResponseTo responseDto;
        try {
            responseDto = commentService.findById(id);
        } catch (Exception ex) {
            return Collections.emptyList();
        }
        return Collections.singletonList(commentKafkaMapper.responseDtoToOutTopicDto(responseDto));
    }

    private List<CommentOutTopicTo> create(CommentInTopicTo dto) {
        Comment newEntity = commentKafkaMapper.toEntity(dto);
        newEntity.setState(CommentState.APPROVE);
        try {
            return Collections.singletonList(commentKafkaMapper.entityToDto(commentRepository.save(newEntity)));
        } catch (Exception ex) {
            newEntity.setState(CommentState.DECLINE);
            return Collections.emptyList();
        }
    }

    private List<CommentOutTopicTo> update(CommentInTopicTo dto) {
        Optional<Comment> optionalNote = commentRepository.findByKeyId(dto.id());
        if (optionalNote.isEmpty()) {
            return Collections.emptyList();
        }
        final Comment entity = optionalNote.get();
        final Comment updated = commentKafkaMapper.partialUpdate(dto, entity);
        return Collections.singletonList(commentKafkaMapper.entityToDto(commentRepository.save(updated)));
    }

    private List<CommentOutTopicTo> delete(Long id) {
        Optional<Comment> optionalNote = commentRepository.findByKeyId(id);
        if (optionalNote.isEmpty()) {
            return Collections.emptyList();
        }
        final Comment deleted = optionalNote.get();
        commentRepository.delete(deleted);
        return Collections.singletonList(commentKafkaMapper.entityToDto(deleted));
    }
}