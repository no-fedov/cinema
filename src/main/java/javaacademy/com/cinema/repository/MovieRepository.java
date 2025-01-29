package javaacademy.com.cinema.repository;

import javaacademy.com.cinema.entity.Movie;
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
public class MovieRepository {
    private final JdbcTemplate jdbcTemplate;

    public Optional<Movie> findById(Integer id) {
        String sql = "select * from movie where id = ?";
        Optional<Movie> currentMovie = jdbcTemplate.query(sql, this::mapToMovie, id).stream().findFirst();
        log.info("Обработан запрос {}, где id = {}. Найдено: {}", sql, id, currentMovie);
        return currentMovie;
    }

    @SneakyThrows
    private Movie mapToMovie(ResultSet rs, int rowNum) {
        Movie movie = new Movie();
        movie.setId(rs.getInt("id"));
        movie.setName(rs.getString("name"));
        movie.setDescription(rs.getString("description"));
        return movie;
    }
}
