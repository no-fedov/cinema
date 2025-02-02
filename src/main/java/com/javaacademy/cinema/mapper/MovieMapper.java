package com.javaacademy.cinema.mapper;

import com.javaacademy.cinema.dto.MovieCreateDto;
import com.javaacademy.cinema.dto.MovieDto;
import com.javaacademy.cinema.entity.Movie;
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
