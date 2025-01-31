package javaacademy.com.cinema.service;

import javaacademy.com.cinema.dto.MovieCreateDto;
import javaacademy.com.cinema.dto.MovieDto;

public interface MovieService {
    MovieDto createMovie(MovieCreateDto dto);
}
