package com.example.rw.kafkacl.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KafkaRequest implements Serializable {
    private  KafkaRequestType requestType;
    private List<String> arguments;
}
