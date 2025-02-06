package com.javaacademy.cinema.service.admin;

import com.javaacademy.cinema.dto.SessionCreateAdminDto;
import com.javaacademy.cinema.dto.SessionAdminDto;

public interface SessionAdminService {
    SessionAdminDto createSession(SessionCreateAdminDto dto);
}
