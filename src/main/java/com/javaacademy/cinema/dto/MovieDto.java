package com.javaacademy.cinema.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        description = "Публичное представление фильма"
)
public class MovieDto {

    @Schema(
            description = "Название фильма",
            example = "Револьвер"
    )
    private String name;

    @Schema(
            description = "Описание фильма",
            example = "Стетхем дерется и филосовствует"
    )
    private String description;
}
