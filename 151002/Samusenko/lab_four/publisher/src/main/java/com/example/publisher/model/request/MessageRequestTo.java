package com.example.publisher.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageRequestTo{
        public BigInteger id;
        public BigInteger issueId;
        @NotBlank
        @Length(min = 2, max = 2048)
        public String content;
}
