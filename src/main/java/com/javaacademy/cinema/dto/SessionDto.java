package com.javaacademy.cinema.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionDto {
    private Integer id;
    @JsonProperty("movie_name")
    private String movieName;
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime date;
    private BigDecimal price;
}
