package com.luschickij.publisher.dto;

import com.luschickij.publisher.model.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class KafkaPostRequestTo {

    UUID id;

    String method;

    Map<String, String> params;

    Post body;

}
