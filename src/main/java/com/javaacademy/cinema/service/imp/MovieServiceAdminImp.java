package com.javaacademy.cinema.service.imp;

import com.javaacademy.cinema.dto.MovieCreateDto;
import com.javaacademy.cinema.dto.MovieDto;
import com.javaacademy.cinema.entity.Movie;
import com.javaacademy.cinema.mapper.MovieMapper;
import com.javaacademy.cinema.repository.MovieRepository;
import com.javaacademy.cinema.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieServiceAdminImp implements MovieService {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    @Override
    public MovieDto createMovie(MovieCreateDto dto) {
        final Movie newMovie = movieMapper.mapToMovie(dto);
        movieRepository.save(newMovie);
        log.info("Сохранен фильм: {}", newMovie);
        return movieMapper.mapToMovieDto(newMovie);
    }
}
