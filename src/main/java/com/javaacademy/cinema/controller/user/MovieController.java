package com.javaacademy.cinema.controller.user;

import com.javaacademy.cinema.dto.MovieDto;
import com.javaacademy.cinema.service.user.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/movie")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping
    public List<MovieDto> getAll() {
        List<MovieDto> movies = movieService.getAll();
        return movies;
    }
}
