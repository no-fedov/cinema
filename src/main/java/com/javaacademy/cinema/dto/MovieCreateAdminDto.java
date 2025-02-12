package com.javaacademy.cinema.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        description = "Представление для администратора при регистрации фильма"
)
public class MovieCreateAdminDto {

    @Schema(
            description = "Название фильма",
            example = "1+1"
    )
    private String name;

    @Schema(
            description = "Описание фильма",
            example = "Фильм про крепкую дружбу"
    )
    private String description;
}
