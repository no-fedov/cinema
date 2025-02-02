package com.javaacademy.cinema.service;

import com.javaacademy.cinema.dto.SessionCreateDto;
import com.javaacademy.cinema.dto.SessionDto;

public interface SessionService {
    SessionDto createSession(SessionCreateDto dto);
}
