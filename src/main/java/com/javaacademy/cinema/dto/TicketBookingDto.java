package com.javaacademy.cinema.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketBookingDto {
    @JsonProperty("session_id")
    private Integer sessionId;
    @JsonProperty("place_name")
    private String placeName;
}
