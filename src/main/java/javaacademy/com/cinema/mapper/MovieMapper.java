package javaacademy.com.cinema.mapper;

import javaacademy.com.cinema.dto.MovieCreateDto;
import javaacademy.com.cinema.dto.MovieDto;
import javaacademy.com.cinema.entity.Movie;
import org.springframework.stereotype.Component;

@Component
public class MovieMapper {

    public Movie mapToMovie(MovieCreateDto dto) {
        return new Movie(
                null,
                dto.getName(),
                dto.getDescription()
        );
    }

    public MovieDto mapToMovieDto(Movie movie) {
        return new MovieDto(
                movie.getId(),
                movie.getName(),
                movie.getDescription()
        );
    }

    public Movie mapToMovie(MovieDto dto) {
        return new Movie(
                dto.getId(),
                dto.getName(),
                dto.getDescription()
        );
    }
}
