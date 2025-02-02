package com.javaacademy.cinema.controller.admin;

import com.javaacademy.cinema.dto.SessionCreateAdminDto;
import com.javaacademy.cinema.dto.SessionAdminDto;
import com.javaacademy.cinema.service.SessionAdminService;
import com.javaacademy.cinema.service.TicketAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/session")
public class SessionAdminController {

    private final AdminValidator adminValidator;
    private final SessionAdminService sessionAdminService;
    private final TicketAdminService ticketAdminService;

    @PostMapping
    public SessionAdminDto createSession(@RequestHeader("user-token") String token,
                                         @RequestBody SessionCreateAdminDto dto) {
        adminValidator.valid(token);
        SessionAdminDto session = sessionAdminService.createSession(dto);
        ticketAdminService.createTicketForSession(session);
        return session;
    }
}
