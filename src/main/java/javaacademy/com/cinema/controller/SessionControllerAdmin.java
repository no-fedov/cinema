package javaacademy.com.cinema.controller;

import javaacademy.com.cinema.config.AdminProperty;
import javaacademy.com.cinema.dto.SessionCreateDto;
import javaacademy.com.cinema.dto.SessionDto;
import javaacademy.com.cinema.service.SessionService;
import javaacademy.com.cinema.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/session")
public class SessionControllerAdmin {

    private final AdminProperty adminProperty;
    private final SessionService sessionService;
    private final TicketService ticketService;

    @PostMapping
    public SessionDto createSession(SessionCreateDto dto) {
        SessionDto session = sessionService.createSession(dto);
        ticketService.createTicketForSession(session);
        return session;
    }
}
