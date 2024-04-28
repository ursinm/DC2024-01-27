package com.example.discussion.kafkaclasses.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import com.example.discussion.kafkaclasses.request.KafkaRequestType;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KafkaResponse implements Serializable {
    private  KafkaRequestType requestType;
    private String responseObject;
    private int status;
}
