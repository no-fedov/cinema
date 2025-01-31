package javaacademy.com.cinema.service.imp;

import javaacademy.com.cinema.dto.SessionCreateDto;
import javaacademy.com.cinema.dto.SessionDto;
import javaacademy.com.cinema.entity.Movie;
import javaacademy.com.cinema.entity.Session;
import javaacademy.com.cinema.mapper.SessionMapper;
import javaacademy.com.cinema.repository.MovieRepository;
import javaacademy.com.cinema.repository.PlaceRepository;
import javaacademy.com.cinema.repository.SessionRepository;
import javaacademy.com.cinema.repository.TicketRepository;
import javaacademy.com.cinema.service.SessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SessionServiceAdminImp implements SessionService {

    private final SessionRepository sessionRepository;
    private final MovieRepository movieRepository;
    private final PlaceRepository placeRepository;
    private final TicketRepository ticketRepository;
    private final SessionMapper sessionMapper;

    @Override
    public SessionDto createSession(SessionCreateDto dto) {
        Integer movieId = dto.getMovieId();
        Movie currentMovie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("кина не будет, оно не найдено"));
        final Session newSession = sessionMapper.mapToSession(dto, currentMovie);
        sessionRepository.save(newSession);
        log.info("Создана сеанс: {}", newSession);
        return sessionMapper.mapToSessionDto(newSession);
    }
}
