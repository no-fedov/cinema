package com.javaacademy.cinema.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        description = "Публичное представление посадочного места"
)
public class PlaceDto {

    @Schema(
            description = "Буквенное название места",
            example = "'1A'"
    )
    private String name;

    @Override
    public String toString() {
        return convertName(this.name);
    }

    private String convertName(String name) {
        String truncate = StringUtils.truncate(name, 1);
        String number = StringUtils.remove(name, truncate);
        return StringUtils.wrap(number + truncate, '\'');
    }
}
