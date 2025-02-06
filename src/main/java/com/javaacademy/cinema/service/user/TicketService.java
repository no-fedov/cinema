package com.javaacademy.cinema.service.user;

import com.javaacademy.cinema.dto.TicketBookingDto;
import com.javaacademy.cinema.dto.TicketDto;

public interface TicketService {

    TicketDto buyTicket(TicketBookingDto dto);
}
