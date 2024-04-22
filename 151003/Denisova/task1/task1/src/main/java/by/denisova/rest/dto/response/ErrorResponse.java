package by.denisova.rest.dto.response;

import java.util.List;

public record ErrorResponse(List<String> messages, Integer code) {
}
