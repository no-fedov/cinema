package javaacademy.com.cinema.repository;

import javaacademy.com.cinema.entity.Session;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class SessionRepository {
    private final JdbcTemplate jdbcTemplate;
    private final MovieRepository movieRepository;

    public Optional<Session> findById(Integer id) {
        String sql = "select * from session where id = ?";
        Optional<Session> currentSession = jdbcTemplate.query(sql, this::mapToSession, id).stream().findFirst();
        log.info("Обработан запрос {}, где id = {}. Найдено: {}", sql, id, currentSession);
        return currentSession;
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
}
