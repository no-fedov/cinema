package com.javaacademy.cinema.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        description = "публичное представление сеанса"
)
public class SessionDto {

    @Schema(
            description = "Номер сеанса",
            example = "1"
    )
    private Integer id;

    @Schema(
            description = "Название фильма",
            example = "Сумерки"
    )
    @JsonProperty("movie_name")
    private String movieName;

    @Schema(
            description = "Дата и время сеанса",
            example = "11.11.2025 11:20",
            type = "string"
    )
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime date;

    @Schema(
            description = "Стоимость сеанса",
            example = "1000"
    )
    private BigDecimal price;
}
