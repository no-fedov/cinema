package com.javaacademy.cinema.service;

import com.javaacademy.cinema.dto.MovieCreateAdminDto;
import com.javaacademy.cinema.dto.MovieAdminDto;

public interface MovieAdminService {
    MovieAdminDto createMovie(MovieCreateAdminDto dto);
}
