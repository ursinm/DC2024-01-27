package com.luschickij.discussion.model;

import com.luschickij.discussion.dto.post.PostRequestTo;
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

    PostRequestTo body;

}
