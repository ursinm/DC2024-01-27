package com.luschickij.publisher.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigInteger;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post extends IdentifiedEntity {

    Long id;

    Long newsId;

    String content;

    String country;

}
