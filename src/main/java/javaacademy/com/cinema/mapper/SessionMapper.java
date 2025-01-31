package javaacademy.com.cinema.mapper;

import javaacademy.com.cinema.dto.MovieDto;
import javaacademy.com.cinema.dto.SessionCreateDto;
import javaacademy.com.cinema.dto.SessionDto;
import javaacademy.com.cinema.entity.Movie;
import javaacademy.com.cinema.entity.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SessionMapper {

    private final MovieMapper movieMapper;

    public Session mapToSession(SessionCreateDto dto, Movie movie) {
        return new Session(
                null,
                dto.getDateTime(),
                dto.getPrice(),
                movie
        );
    }

    public Session mapToSession(SessionDto sessionDto) {
        Session session = new Session();
        session.setId(sessionDto.getId());
        session.setPrice(sessionDto.getPrice());
        session.setDateTime(sessionDto.getDateTime());
        session.setMovie(movieMapper.mapToMovie(sessionDto.getMovie()));
        return session;
    }

    public SessionDto mapToSessionDto(Session session) {
        return new SessionDto(
                session.getId(),
                session.getPrice(),
                session.getDateTime(),
                movieMapper.mapToMovieDto(session.getMovie())
        );
    }


}
