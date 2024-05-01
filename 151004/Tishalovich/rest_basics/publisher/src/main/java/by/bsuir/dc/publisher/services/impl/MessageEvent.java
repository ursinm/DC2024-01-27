package by.bsuir.dc.publisher.services.impl;

import by.bsuir.dc.publisher.entities.dtos.request.MessageRequestTo;
import by.bsuir.dc.publisher.services.exceptions.ErrorInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageEvent {

    private UUID id;

    private Operation operation;

    private MessageRequestTo messageRequestTo;

    private List<MessageRequestTo> messageRequestTos;

    private ErrorInfo errorInfo;

}
