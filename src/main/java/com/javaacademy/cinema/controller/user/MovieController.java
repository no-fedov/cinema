package com.javaacademy.cinema.controller.user;

import com.javaacademy.cinema.controller.ErrorResponse;
import com.javaacademy.cinema.dto.MovieDto;
import com.javaacademy.cinema.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/movie")
@RequiredArgsConstructor
@Tag(
        name = "Public: Фильмы",
        description = "Публичный API для работы с фильмами"
)
@ApiResponses(
        @ApiResponse(
                responseCode = "500",
                description = "Ошибка на сервере",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ErrorResponse.class)
                )
        )
)
public class MovieController {

    private final MovieService movieService;

    @Operation(
            summary = "Находит фильмы",
            description = "Находит все существующие в БД фильмы"
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Найдены фильмы",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(
                                                    implementation = MovieDto.class
                                            )
                                    )
                            )
                    )
            }
    )
    @GetMapping
    public List<MovieDto> getAll() {
        List<MovieDto> movies = movieService.getAll();
        return movies;
    }
}
