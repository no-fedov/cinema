package com.javaacademy.cinema.service.admin.imp;

import com.javaacademy.cinema.dto.MovieCreateAdminDto;
import com.javaacademy.cinema.dto.MovieAdminDto;
import com.javaacademy.cinema.entity.Movie;
import com.javaacademy.cinema.mapper.MovieMapper;
import com.javaacademy.cinema.repository.MovieRepository;
import com.javaacademy.cinema.service.admin.MovieAdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieAdminServiceImp implements MovieAdminService {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    @Override
    public MovieAdminDto createMovie(MovieCreateAdminDto dto) {
        final Movie newMovie = movieMapper.mapToMovie(dto);
        movieRepository.save(newMovie);
        log.info("Сохранен фильм: {}", newMovie);
        return movieMapper.mapToMovieAdminDto(newMovie);
    }
}
