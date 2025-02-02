package com.javaacademy.cinema.controller.admin;

import com.javaacademy.cinema.dto.TicketDto;
import com.javaacademy.cinema.service.TicketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.lang.Boolean.TRUE;

@Slf4j
@RestController
@RequestMapping("/ticket")
@RequiredArgsConstructor
public class TicketControllerAdmin {

    private final AdminValidator adminValidator;
    private final TicketService ticketService;

    @GetMapping("/saled")
    public List<TicketDto> soldTickets(@RequestHeader("user-token") String token) {
        adminValidator.valid(token);
        List<TicketDto> soldTickets = ticketService.getTicketsBySoldCondition(TRUE);
        log.info("Найдены все проданные билеты: {}", soldTickets);
        return soldTickets;
    }

}
