package com.example.discussion.kafkaclasses.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KafkaRequest implements Serializable {
    private KafkaRequestType requestType;
    private List<String> arguments;
}
