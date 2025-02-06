package com.javaacademy.cinema.mapper;

import com.javaacademy.cinema.dto.SessionAdminDto;
import com.javaacademy.cinema.dto.SessionCreateAdminDto;
import com.javaacademy.cinema.dto.SessionDto;
import com.javaacademy.cinema.entity.Movie;
import com.javaacademy.cinema.entity.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SessionMapper {

    private final MovieMapper movieMapper;

    public Session mapToSession(SessionCreateAdminDto dto, Movie movie) {
        return new Session(
                null,
                dto.getDateTime(),
                dto.getPrice(),
                movie
        );
    }

    public Session mapToSession(SessionAdminDto sessionAdminDto) {
        Session session = new Session();
        session.setId(sessionAdminDto.getId());
        session.setPrice(sessionAdminDto.getPrice());
        session.setDateTime(sessionAdminDto.getDateTime());
        session.setMovie(movieMapper.mapToMovie(sessionAdminDto.getMovie()));
        return session;
    }

    public SessionAdminDto mapToSessionAdminDto(Session session) {
        return new SessionAdminDto(
                session.getId(),
                session.getPrice(),
                session.getDateTime(),
                movieMapper.mapToMovieAdminDto(session.getMovie())
        );
    }

    public SessionDto mapToSessionDto(Session session) {
        return new SessionDto(
                session.getId(),
                session.getMovie().getName(),
                session.getDateTime(),
                session.getPrice()
        );

    }

    public List<SessionDto> mapToSessionDto(List<Session> sessions) {
        return sessions.stream().
                map(this::mapToSessionDto)
                .toList();
    }
}
