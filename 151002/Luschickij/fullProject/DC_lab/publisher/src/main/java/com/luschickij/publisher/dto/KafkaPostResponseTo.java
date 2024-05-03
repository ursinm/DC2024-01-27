package com.luschickij.publisher.dto;

import com.luschickij.publisher.model.Post;
import com.luschickij.publisher.model.RestError;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class KafkaPostResponseTo {

    UUID requestId;

    String status;

    Map<String, String> params;

    List<Post> posts;

    RestError error;
}
