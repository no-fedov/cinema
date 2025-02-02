package com.javaacademy.cinema.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionAdminDto {
    private Integer id;
    private BigDecimal price;
    @JsonProperty("date_time")
    private LocalDateTime dateTime;
    private MovieAdminDto movie;

}
