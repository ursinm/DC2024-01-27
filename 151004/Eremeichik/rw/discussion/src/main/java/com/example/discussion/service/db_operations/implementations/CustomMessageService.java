package com.example.discussion.service.db_operations.implementations;

import com.example.discussion.exception.model.not_found.EntityNotFoundException;
import com.example.discussion.model.entity.implementations.Message;
import com.example.discussion.repository.interfaces.MessageRepository;
import com.example.discussion.service.db_operations.interfaces.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class CustomMessageService implements MessageService {
    private final AtomicLong id = new AtomicLong(0L);
    private final MessageRepository messageRepository;

    @Override
    public Message findById(Long id) throws EntityNotFoundException {
        return messageRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));
    }

    @Override
    public List<Message> findAll() {
        return messageRepository.findAll();
    }

    @Override
    public void save(Message entity) {
        entity.setId(id.incrementAndGet());
        messageRepository.save(entity);
    }

    @Override
    public void deleteById(Long id) throws EntityNotFoundException {
        boolean wasDeleted = messageRepository.findById(id).isPresent();
        if(!wasDeleted){
            throw new EntityNotFoundException(id);
        } else{
            messageRepository.deleteById(id);
        }
    }

    @Override
    public void update(Message entity) {
        boolean wasUpdated = messageRepository.findById(entity.getId()).isPresent();
        if(!wasUpdated){
            throw new EntityNotFoundException();
        } else{
            messageRepository.save(entity);
        }
    }
}
