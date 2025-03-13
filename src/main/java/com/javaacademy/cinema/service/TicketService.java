package com.javaacademy.cinema.service;

import com.javaacademy.cinema.dto.SessionAdminDto;
import com.javaacademy.cinema.dto.TicketAdminDto;
import com.javaacademy.cinema.dto.TicketBookingDto;
import com.javaacademy.cinema.dto.TicketDto;

import java.util.List;

public interface TicketService {

    TicketDto buy(TicketBookingDto dto);

    List<TicketAdminDto> createForSession(SessionAdminDto dto);

    List<TicketAdminDto> getBySoldCondition(boolean isSold);
}
