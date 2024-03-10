package by.bsuir.taskrest.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ErrorResponse(
        String message,
        Integer code,
        LocalDateTime timestamp
) {
    public static class ErrorResponseBuilder {
        public ErrorResponseBuilder code(Integer code) {
            this.code = code * 100;
            return this;
        }

        public ErrorResponseBuilder subCode(Integer subCode) {
            this.code += subCode;
            return this;
        }
    }
}
