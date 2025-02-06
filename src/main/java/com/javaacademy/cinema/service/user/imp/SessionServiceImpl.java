package com.javaacademy.cinema.service.user.imp;

import com.javaacademy.cinema.dto.SessionDto;
import com.javaacademy.cinema.entity.Session;
import com.javaacademy.cinema.mapper.SessionMapper;
import com.javaacademy.cinema.repository.SessionRepository;
import com.javaacademy.cinema.service.user.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;
    private final SessionMapper sessionMapper;

    @Override
    public List<SessionDto> getAll() {
        List<Session> sessions = sessionRepository.findAll();
        List<SessionDto> sessionDtos = sessionMapper.mapToSessionDto(sessions);
        return sessionDtos;
    }
}
