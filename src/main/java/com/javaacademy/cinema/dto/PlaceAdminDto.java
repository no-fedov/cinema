package com.javaacademy.cinema.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        description = "Представление посадочного места для администратора"
)
public class PlaceAdminDto {

    @Schema(
            description = "Номер посадочного места",
            example = "1"
    )
    private Integer id;

    @Schema(
            description = "Буквенное название места",
            example = "А1"
    )
    private String name;
}
