package by.bsuir.RV_Project.services;

import by.bsuir.RV_Project.dto.requests.MessageRequestDto;
import by.bsuir.RV_Project.dto.responses.MessageResponseDto;

import java.util.List;

public interface MessageService extends BaseService<MessageRequestDto, MessageResponseDto> {
    List<MessageResponseDto> readAll();
}
