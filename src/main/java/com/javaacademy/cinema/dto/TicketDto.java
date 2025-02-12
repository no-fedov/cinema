package com.javaacademy.cinema.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Публичное представление билета")
public class TicketDto {
    @Schema(
            description = "Порядковый номер билета",
            example = "1"
    )
    @JsonProperty("ticket_id")
    private Integer id;

    @Schema(
            description = "Наименование места посадки",
            example = "'1A'"
    )
    @JsonProperty("place_name")
    private String placeName;

    @Schema(
            description = "Название фильма",
            example = "Красная жара"
    )
    @JsonProperty("movie_name")
    private String movieName;

    @Schema(
            description = "Дата и время начала сеанса",
            example = "10.01.2025 11:00",
            type = "string"
    )
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime date;
}
