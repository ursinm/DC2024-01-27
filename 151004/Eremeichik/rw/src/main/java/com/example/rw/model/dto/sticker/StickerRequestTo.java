package com.example.rw.model.dto.sticker;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StickerRequestTo {

    private Long id;
    @NotBlank
    @Size(min = 2, max = 32)
    private String name;
}
