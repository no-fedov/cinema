package com.javaacademy.cinema.service;

import com.javaacademy.cinema.dto.MovieCreateDto;
import com.javaacademy.cinema.dto.MovieDto;

public interface MovieService {
    MovieDto createMovie(MovieCreateDto dto);
}
