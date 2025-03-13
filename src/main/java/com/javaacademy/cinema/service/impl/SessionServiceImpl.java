package com.javaacademy.cinema.service.impl;

import com.javaacademy.cinema.dto.SessionAdminDto;
import com.javaacademy.cinema.dto.SessionCreateAdminDto;
import com.javaacademy.cinema.dto.SessionDto;
import com.javaacademy.cinema.entity.Movie;
import com.javaacademy.cinema.entity.Session;
import com.javaacademy.cinema.exception.NotFoundEntityException;
import com.javaacademy.cinema.mapper.SessionMapper;
import com.javaacademy.cinema.repository.MovieRepository;
import com.javaacademy.cinema.repository.SessionRepository;
import com.javaacademy.cinema.service.SessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

    private final MovieRepository movieRepository;
    private final SessionRepository sessionRepository;
    private final SessionMapper sessionMapper;

    @Override
    public SessionAdminDto create(SessionCreateAdminDto dto) {
        Integer movieId = dto.getMovieId();
        Movie currentMovie = movieRepository.findById(movieId)
                .orElseThrow(() -> new NotFoundEntityException("Кино с id =%s не найдено".formatted(movieId)));
        final Session newSession = sessionMapper.mapToSession(dto, currentMovie);
        sessionRepository.save(newSession);
        log.info("Создана сеанс: {}", newSession);
        return sessionMapper.mapToSessionAdminDto(newSession);
    }

    @Override
    public List<SessionDto> getAll() {
        List<Session> sessions = sessionRepository.findAll();
        List<SessionDto> sessionDtos = sessionMapper.mapToSessionDto(sessions);
        log.info("Найдены все существующие сеансы: {}", sessionDtos);
        return sessionDtos;
    }
}
