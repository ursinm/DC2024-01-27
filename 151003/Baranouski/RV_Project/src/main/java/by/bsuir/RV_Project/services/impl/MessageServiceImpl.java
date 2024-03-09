package by.bsuir.RV_Project.services.impl;

import by.bsuir.RV_Project.domain.Message;
import by.bsuir.RV_Project.dto.requests.MessageRequestDto;
import by.bsuir.RV_Project.dto.requests.converters.MessageRequestConverter;
import by.bsuir.RV_Project.dto.responses.MessageResponseDto;
import by.bsuir.RV_Project.dto.responses.converters.CollectionMessageResponseConverter;
import by.bsuir.RV_Project.dto.responses.converters.MessageResponseConverter;
import by.bsuir.RV_Project.exceptions.EntityExistsException;
import by.bsuir.RV_Project.exceptions.Messages;
import by.bsuir.RV_Project.exceptions.NoEntityExistsException;
import by.bsuir.RV_Project.repositories.MessageRepository;
import by.bsuir.RV_Project.services.MessageService;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Validated
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final MessageRequestConverter messageRequestConverter;
    private final MessageResponseConverter messageResponseConverter;
    private final CollectionMessageResponseConverter collectionMessageResponseConverter;
    @Override
    @Validated
    public MessageResponseDto create(@Valid @NonNull MessageRequestDto dto) throws EntityExistsException {
        Optional<Message> message = dto.getId() == null ? Optional.empty() : messageRepository.findById(dto.getId());
        if (message.isEmpty()) {
            return messageResponseConverter.toDto(messageRepository.save(messageRequestConverter.fromDto(dto)));
        } else {
            throw new EntityExistsException(Messages.EntityExistsException);
        }
    }

    @Override
    public Optional<MessageResponseDto> read(@NonNull Long id) {
        return messageRepository.findById(id).flatMap(author -> Optional.of(
                messageResponseConverter.toDto(author)));
    }

    @Override
    @Validated
    public MessageResponseDto update(@Valid @NonNull MessageRequestDto dto) throws NoEntityExistsException {
        Optional<Message> message = dto.getId() == null || messageRepository.findById(dto.getId()).isEmpty() ?
                Optional.empty() : Optional.of(messageRequestConverter.fromDto(dto));
        return messageResponseConverter.toDto(messageRepository.save(message.orElseThrow(() ->
                new NoEntityExistsException(Messages.NoEntityExistsException))));
    }

    @Override
    public Long delete(@NonNull Long id) throws NoEntityExistsException {
        Optional<Message> message = messageRepository.findById(id);
        messageRepository.deleteById(message.map(Message::getId).orElseThrow(() ->
                new NoEntityExistsException(Messages.NoEntityExistsException)));
        return message.get().getId();
    }

    @Override
    public List<MessageResponseDto> readAll() {
        return collectionMessageResponseConverter.toListDto(messageRepository.findAll());
    }
}
