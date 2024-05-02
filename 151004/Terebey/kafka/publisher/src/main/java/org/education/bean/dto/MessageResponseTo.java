package org.education.bean.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class MessageResponseTo {
    int id;
    int issueId;
    String content;
}