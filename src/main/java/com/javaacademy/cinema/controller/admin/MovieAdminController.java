package com.javaacademy.cinema.controller.admin;

import com.javaacademy.cinema.dto.MovieCreateAdminDto;
import com.javaacademy.cinema.dto.MovieAdminDto;
import com.javaacademy.cinema.service.admin.MovieAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movie")
public class MovieAdminController {

    private final AdminValidator adminValidator;
    private final MovieAdminService movieAdminService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public MovieAdminDto createMovie(@RequestHeader("user-token") String token,
                                     @RequestBody MovieCreateAdminDto dto) {
        adminValidator.valid(token);
        MovieAdminDto movie = movieAdminService.createMovie(dto);
        return movie;
    }
}
