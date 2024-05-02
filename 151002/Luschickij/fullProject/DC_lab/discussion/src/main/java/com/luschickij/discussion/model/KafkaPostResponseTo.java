package com.luschickij.discussion.model;

import com.luschickij.discussion.dto.post.PostResponseTo;
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

    List<PostResponseTo> posts;

    RestError error;
}
