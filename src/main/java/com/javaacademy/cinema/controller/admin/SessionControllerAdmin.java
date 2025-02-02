package com.javaacademy.cinema.controller.admin;

import com.javaacademy.cinema.dto.SessionCreateDto;
import com.javaacademy.cinema.dto.SessionDto;
import com.javaacademy.cinema.service.SessionService;
import com.javaacademy.cinema.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/session")
public class SessionControllerAdmin {

    private final AdminValidator adminValidator;
    private final SessionService sessionService;
    private final TicketService ticketService;

    @PostMapping
    public SessionDto createSession(@RequestHeader("user-token") String token,
                                    @RequestBody SessionCreateDto dto) {
        adminValidator.valid(token);
        SessionDto session = sessionService.createSession(dto);
        ticketService.createTicketForSession(session);
        return session;
    }
}
