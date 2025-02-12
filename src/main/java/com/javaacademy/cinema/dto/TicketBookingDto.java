package com.javaacademy.cinema.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        description = "Представление для покупки билета"
)
public class TicketBookingDto {

    @Schema(
            description = "Порядковый номер сессии",
            example = "1"
    )
    @JsonProperty("session_id")
    private Integer sessionId;


    @Schema(
            description = "Наименование места посадки",
            example = "A1"
    )
    @JsonProperty("place_name")
    private String placeName;
}
