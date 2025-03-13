package com.javaacademy.cinema.service;

import com.javaacademy.cinema.dto.MovieAdminDto;
import com.javaacademy.cinema.dto.MovieCreateAdminDto;
import com.javaacademy.cinema.dto.MovieDto;

import java.util.List;

public interface MovieService {

    MovieAdminDto create(MovieCreateAdminDto dto);

    List<MovieDto> getAll();
}
