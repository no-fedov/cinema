package com.javaacademy.cinema.controller.user;

import com.javaacademy.cinema.dto.PlaceDto;
import com.javaacademy.cinema.dto.SessionDto;
import com.javaacademy.cinema.service.user.PlaceService;
import com.javaacademy.cinema.service.user.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/session")
@RequiredArgsConstructor
public class SessionController {

    private final SessionService sessionService;
    private final PlaceService placeService;

    @GetMapping
    public List<SessionDto> getAll() {
        List<SessionDto> sessions = sessionService.getAll();
        return sessions;
    }

    @GetMapping("/{id}/free-place")
    public String freeSeatsBySessionId(@PathVariable("id") Integer sessionId) {
        List<PlaceDto> freePlace = placeService.getFreeBySessionId(sessionId);
        String placeAsString = freePlace.stream().map(PlaceDto::toString)
                .reduce((e1, e2) -> e1.concat(", ").concat(e2)).orElse("");
        return "[".concat(placeAsString).concat("]");
    }
}
