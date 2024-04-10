package com.example.rw.kafkacl.response;

import com.example.rw.kafkacl.request.KafkaRequestType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KafkaResponse implements Serializable {
    private KafkaRequestType requestType;
    private String responseObject;
    private int status;
}
