package com.javaacademy.cinema.service.impl;

import com.javaacademy.cinema.dto.MovieAdminDto;
import com.javaacademy.cinema.dto.MovieCreateAdminDto;
import com.javaacademy.cinema.dto.MovieDto;
import com.javaacademy.cinema.entity.Movie;
import com.javaacademy.cinema.mapper.MovieMapper;
import com.javaacademy.cinema.repository.MovieRepository;
import com.javaacademy.cinema.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    @Override
    public MovieAdminDto create(MovieCreateAdminDto dto) {
        final Movie newMovie = movieMapper.mapToMovie(dto);
        movieRepository.save(newMovie);
        log.info("Сохранен фильм: {}", newMovie);
        return movieMapper.mapToMovieAdminDto(newMovie);
    }

    @Override
    public List<MovieDto> getAll() {
        List<Movie> movies = movieRepository.findAll();
        List<MovieDto> moviesDto = movieMapper.mapToMovieDto(movies);
        log.info("Найдены фильмы: {}", moviesDto);
        return moviesDto;
    }
}
