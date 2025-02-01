package javaacademy.com.cinema.controller;

import javaacademy.com.cinema.dto.TicketDto;
import javaacademy.com.cinema.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ticket")
@RequiredArgsConstructor
public class TicketControllerAdmin {

    private final TicketService ticketService;

    @GetMapping("/saled")
    public List<TicketDto> soldTickets() {

    }

}
