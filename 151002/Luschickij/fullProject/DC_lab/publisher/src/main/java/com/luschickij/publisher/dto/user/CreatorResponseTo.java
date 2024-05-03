package com.luschickij.publisher.dto.creator;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.luschickij.publisher.model.IdentifiedEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigInteger;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@JsonRootName("creator")
public class CreatorResponseTo extends IdentifiedEntity {

    private Long id;
    private String login;
    private String hashedPassword;
    private String firstname;
    private String lastname;

}