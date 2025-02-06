package com.javaacademy.cinema.controller.user;

import com.javaacademy.cinema.dto.TicketBookingDto;
import com.javaacademy.cinema.dto.TicketDto;
import com.javaacademy.cinema.service.user.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ticket")
public class TicketController {

    private final TicketService ticketService;

    @PostMapping("/booking")
    public TicketDto buyTicket(TicketBookingDto dto) {
        TicketDto ticketDto = ticketService.buyTicket(dto);
        return ticketDto;
    }
}
