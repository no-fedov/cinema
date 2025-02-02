package com.javaacademy.cinema.service;

import com.javaacademy.cinema.dto.SessionDto;
import com.javaacademy.cinema.dto.TicketDto;

import java.util.List;

public interface TicketService {

    List<TicketDto> createTicketForSession(SessionDto dto);

    List<TicketDto> getTicketsBySoldCondition(boolean isSold);
}
