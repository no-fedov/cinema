package javaacademy.com.cinema.service.imp;

import javaacademy.com.cinema.dto.MovieCreateDto;
import javaacademy.com.cinema.dto.MovieDto;
import javaacademy.com.cinema.entity.Movie;
import javaacademy.com.cinema.mapper.MovieMapper;
import javaacademy.com.cinema.repository.MovieRepository;
import javaacademy.com.cinema.service.MovieService;
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
