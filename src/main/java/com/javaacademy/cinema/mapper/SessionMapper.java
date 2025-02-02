package com.javaacademy.cinema.mapper;

import com.javaacademy.cinema.dto.SessionCreateAdminDto;
import com.javaacademy.cinema.dto.SessionAdminDto;
import com.javaacademy.cinema.entity.Movie;
import com.javaacademy.cinema.entity.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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

    public SessionAdminDto mapToSessionDto(Session session) {
        return new SessionAdminDto(
                session.getId(),
                session.getPrice(),
                session.getDateTime(),
                movieMapper.mapToMovieDto(session.getMovie())
        );
    }


}
