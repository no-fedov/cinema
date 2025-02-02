package com.javaacademy.cinema.repository.imp;

import com.javaacademy.cinema.entity.Movie;
import com.javaacademy.cinema.entity.Session;
import com.javaacademy.cinema.repository.MovieRepository;
import com.javaacademy.cinema.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class SessionRepositoryImp implements SessionRepository {

    private static final String SESSION_BY_ID_QUERY = "select * from session where id = ?";
    private static final String SAVE_SESSION = """
                insert into session (movie_id, price, datetime)
                values (?, ?, ?) returning id
            """;
    private static final String ALL_SESSION_QUERY = """
            select s.id as session_id,
                s.price,
                s.datetime,
                m.id as movie_id,
                m.name as movie_name,
                m.description as movie_description
            from session s
                left join movie m on m.id = s.movie_id;
            """;

    private final JdbcTemplate jdbcTemplate;
    private final MovieRepository movieRepository;

    public Optional<Session> findById(Integer id) {
        Optional<Session> currentSession = jdbcTemplate.query(
                SESSION_BY_ID_QUERY,
                this::mapToSession,
                id
        ).stream().findFirst();
        log.info("Обработан запрос {}, где id = {}. Найдено: {}", SESSION_BY_ID_QUERY, id, currentSession);
        return currentSession;
    }

    public Session save(final Session newSession) {
        Integer movieId = newSession.getMovie().getId();
        Integer sessionId = jdbcTemplate.queryForObject(
                SAVE_SESSION,
                Integer.class,
                movieId,
                newSession.getPrice(),
                newSession.getDateTime()
        );
        newSession.setId(sessionId);
        return newSession;
    }

    public List<Session> findAll() {
        List<Session> sessions = jdbcTemplate.query(
                ALL_SESSION_QUERY,
                this::mapToSessionForList
        );
        log.info("Найдены сессии: {}", sessions);
        return sessions;
    }

    @SneakyThrows
    private Session mapToSession(ResultSet rs, int rowNum) {
        Session session = new Session();
        session.setId(rs.getInt("id"));
        session.setPrice(rs.getBigDecimal("price"));
        movieRepository.findById(rs.getInt("movie_id")).ifPresent(session::setMovie);
        session.setDateTime(rs.getTimestamp("datetime").toLocalDateTime());
        return session;
    }

    @SneakyThrows
    private Session mapToSessionForList(ResultSet rs, int rowNum) {
        Session session = new Session();
        session.setId(rs.getInt("session_id"));
        session.setPrice(rs.getBigDecimal("price"));
        session.setDateTime(rs.getTimestamp("datetime").toLocalDateTime());
        String movieId = rs.getString("movie_id");
        if (Objects.nonNull(movieId)) {
            Movie movie = new Movie();
            movie.setId(Integer.parseInt(movieId));
            movie.setName(rs.getString("movie_name"));
            movie.setDescription(rs.getString("movie_description"));
            session.setMovie(movie);
        }
        return session;
    }
}
