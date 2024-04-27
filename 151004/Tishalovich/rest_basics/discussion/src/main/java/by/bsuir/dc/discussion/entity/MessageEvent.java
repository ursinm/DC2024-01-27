package by.bsuir.dc.discussion.entity;

import by.bsuir.dc.discussion.service.exception.ErrorInfo;
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
