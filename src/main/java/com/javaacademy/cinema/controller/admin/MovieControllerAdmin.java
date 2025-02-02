package com.javaacademy.cinema.controller.admin;

import com.javaacademy.cinema.dto.MovieCreateDto;
import com.javaacademy.cinema.dto.MovieDto;
import com.javaacademy.cinema.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movie")
public class MovieControllerAdmin {

    private final AdminValidator adminValidator;
    private final MovieService movieService;

    @PostMapping
    public MovieDto createMovie(@RequestHeader("user-token") String token,
                            @RequestBody MovieCreateDto dto) {
        adminValidator.valid(token);
        MovieDto movie = movieService.createMovie(dto);
        return movie;
    }
}
