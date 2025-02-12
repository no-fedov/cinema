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
        description = "Представление сеанса для администратора"
)
public class SessionAdminDto {

    @Schema(
            description = "Номер сеанса",
            example = "1"
    )
    private Integer id;

    @Schema(
            description = "Стоимость сеанса",
            example = "1000"
    )
    private BigDecimal price;

    @Schema(
            description = "Дата и время сеанса",
            example = "11.11.2022 11:10",
            type = "string"
    )
    @JsonProperty("date_time")
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime dateTime;

    @Schema(
            description = "Текущий фильм"
    )
    private MovieAdminDto movie;
}
