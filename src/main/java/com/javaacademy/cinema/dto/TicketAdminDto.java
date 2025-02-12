package com.javaacademy.cinema.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        description = "Представление билета на сеанс для администратора"
)
public class TicketAdminDto {

    @Schema(
            description = "Номер билета",
            example = "1"
    )
    private Integer id;
    private SessionAdminDto session;
    private PlaceAdminDto place;

    @Schema(
            description = "Куплен ли билет?"
    )
    private Boolean isSold;
}
