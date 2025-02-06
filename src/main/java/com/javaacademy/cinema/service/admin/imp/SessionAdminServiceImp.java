package com.javaacademy.cinema.service.admin.imp;

import com.javaacademy.cinema.repository.SessionRepository;
import com.javaacademy.cinema.repository.TicketRepository;
import com.javaacademy.cinema.dto.SessionCreateAdminDto;
import com.javaacademy.cinema.dto.SessionAdminDto;
import com.javaacademy.cinema.entity.Movie;
import com.javaacademy.cinema.entity.Session;
import com.javaacademy.cinema.mapper.SessionMapper;
import com.javaacademy.cinema.repository.MovieRepository;
import com.javaacademy.cinema.repository.PlaceRepository;
import com.javaacademy.cinema.service.admin.SessionAdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SessionAdminServiceImp implements SessionAdminService {

    private final SessionRepository sessionRepository;
    private final MovieRepository movieRepository;
    private final PlaceRepository placeRepository;
    private final TicketRepository ticketRepository;
    private final SessionMapper sessionMapper;

    @Override
    public SessionAdminDto createSession(SessionCreateAdminDto dto) {
        Integer movieId = dto.getMovieId();
        Movie currentMovie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("кина не будет, оно не найдено"));
        final Session newSession = sessionMapper.mapToSession(dto, currentMovie);
        sessionRepository.save(newSession);
        log.info("Создана сеанс: {}", newSession);
        return sessionMapper.mapToSessionAdminDto(newSession);
    }
}
