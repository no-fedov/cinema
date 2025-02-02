package com.javaacademy.cinema.mapper;

import com.javaacademy.cinema.dto.MovieCreateAdminDto;
import com.javaacademy.cinema.dto.MovieAdminDto;
import com.javaacademy.cinema.entity.Movie;
import org.springframework.stereotype.Component;

@Component
public class MovieMapper {

    public Movie mapToMovie(MovieCreateAdminDto dto) {
        return new Movie(
                null,
                dto.getName(),
                dto.getDescription()
        );
    }

    public MovieAdminDto mapToMovieDto(Movie movie) {
        return new MovieAdminDto(
                movie.getId(),
                movie.getName(),
                movie.getDescription()
        );
    }

    public Movie mapToMovie(MovieAdminDto dto) {
        return new Movie(
                dto.getId(),
                dto.getName(),
                dto.getDescription()
        );
    }
}
