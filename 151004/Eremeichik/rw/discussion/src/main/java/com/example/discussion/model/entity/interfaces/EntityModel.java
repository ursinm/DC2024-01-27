package com.example.discussion.model.entity.interfaces;

import java.io.Serializable;

public interface EntityModel<I> extends Serializable {

    I getId();

    void setId(I id);
}
