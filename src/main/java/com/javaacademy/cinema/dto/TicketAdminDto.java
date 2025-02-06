package com.javaacademy.cinema.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketAdminDto {
    private Integer id;
    private SessionAdminDto session;
    private PlaceAdminDto place;
    private Boolean isSold;
}
