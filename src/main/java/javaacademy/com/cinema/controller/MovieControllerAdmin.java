package javaacademy.com.cinema.controller;

import javaacademy.com.cinema.config.AdminProperty;
import javaacademy.com.cinema.dto.MovieCreateDto;
import javaacademy.com.cinema.dto.MovieDto;
import javaacademy.com.cinema.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movie")
public class MovieControllerAdmin {

    private final AdminProperty adminProperty;
    private final MovieService movieService;

    @PostMapping
    public MovieDto createMovie(@RequestHeader("user-token") String token,
                            @RequestBody MovieCreateDto dto) {
        if (!Objects.equals(token, adminProperty.getToken())) {
            throw new RuntimeException("вы не админ");
        }
        MovieDto movie = movieService.createMovie(dto);
        return movie;
    }
}
