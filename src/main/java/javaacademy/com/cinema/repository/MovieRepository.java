package javaacademy.com.cinema.repository;

import javaacademy.com.cinema.entity.Movie;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MovieRepository {

    private static final String MOVIE_BY_ID_QUERY = "select * from movie where id = ?";
    private static final String SAVE_MOVIE_QUERY = "insert into movie (name, description) values(?, ?) returning id";
    private static final String ALL_MOVIES_QUERY = "select * from movie";

    private final JdbcTemplate jdbcTemplate;

    public Optional<Movie> findById(Integer id) {
        Optional<Movie> currentMovie = jdbcTemplate.query(
                MOVIE_BY_ID_QUERY,
                this::mapToMovie,
                id
        ).stream().findFirst();
        log.info("Обработан запрос {}, где id = {}. Найдено: {}", MOVIE_BY_ID_QUERY, id, currentMovie);
        return currentMovie;
    }

    public Movie save(final Movie newMovie) {
        Integer id = jdbcTemplate.queryForObject(
                SAVE_MOVIE_QUERY,
                Integer.class,
                newMovie.getName(),
                newMovie.getDescription()
        );
        newMovie.setId(id);
        log.info("Сохранен новый фильм: {}", newMovie);
        return newMovie;
    }

    public List<Movie> findAll() {
        List<Movie> allMovies = jdbcTemplate.query(
                ALL_MOVIES_QUERY,
                this::mapToMovie
        );
        log.info("Найдены фильмы: {}", allMovies);
        return allMovies;
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
