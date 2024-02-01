package com.poluectov.rvlab1.model;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigInteger;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class IdentifiedEntity {

    @Nullable
    private BigInteger id = null;

}
