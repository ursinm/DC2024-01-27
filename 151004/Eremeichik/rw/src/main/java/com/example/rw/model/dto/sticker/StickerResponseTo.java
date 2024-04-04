package com.example.rw.model.dto.sticker;

import com.example.rw.model.entity.implementations.News;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StickerResponseTo {

    private String name;
    private Long id;
}
