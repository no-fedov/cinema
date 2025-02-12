package com.javaacademy.cinema.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        description = "Представление фильма для администратора"
)
public class MovieAdminDto {
    @Schema(
            description = "Порядковый номер фильма",
            example = "1"
    )
    private Integer id;

    @Schema(
            description = "Название фильма",
            example = "Очень страшное кино"
    )
    private String name;

    @Schema(
            description = "Описание фильма",
            example = "Комедия с элементами ужаса"
    )
    private String description;
}
