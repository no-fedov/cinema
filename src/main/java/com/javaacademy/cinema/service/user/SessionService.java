package com.javaacademy.cinema.service.user;

import com.javaacademy.cinema.dto.SessionDto;

import java.util.List;


public interface SessionService {

    List<SessionDto> getAll();
}
