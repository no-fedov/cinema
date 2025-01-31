package javaacademy.com.cinema.service;

import javaacademy.com.cinema.dto.SessionCreateDto;
import javaacademy.com.cinema.dto.SessionDto;

public interface SessionService {
    SessionDto createSession(SessionCreateDto dto);
}
