package com.javaacademy.cinema.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketDto {
    @JsonProperty("ticket_id")
    private Integer id;
    @JsonProperty("place_name")
    private String placeName;
    @JsonProperty("movie_name")
    private String movieName;
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime date;
}
