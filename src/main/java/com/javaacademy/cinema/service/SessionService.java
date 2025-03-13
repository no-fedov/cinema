package com.javaacademy.cinema.service;

import com.javaacademy.cinema.dto.SessionAdminDto;
import com.javaacademy.cinema.dto.SessionCreateAdminDto;
import com.javaacademy.cinema.dto.SessionDto;

import java.util.List;


public interface SessionService {

    SessionAdminDto create(SessionCreateAdminDto dto);

    List<SessionDto> getAll();
}
