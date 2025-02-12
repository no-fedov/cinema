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
      description = "Представление для администратора при создании сеанса"
)
public class SessionCreateAdminDto {

    @Schema(
            description = "Номер фильма",
            example = "1"
    )
    @JsonProperty("movie_id")
    private Integer movieId;

    @Schema(
            description = "Дата и время сеанса",
            example = "11.11.2020 20:20",
            type = "string"
    )
    @JsonProperty("date_time")
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime dateTime;

    @Schema(
            description = "Стоимость сеанса",
            example = "1000"
    )
    private BigDecimal price;
}
