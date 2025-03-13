package com.javaacademy.cinema.controller.admin;

import com.javaacademy.cinema.controller.ErrorResponse;
import com.javaacademy.cinema.dto.MovieAdminDto;
import com.javaacademy.cinema.dto.MovieCreateAdminDto;
import com.javaacademy.cinema.service.AdminValidator;
import com.javaacademy.cinema.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(
        name = "Admin: Фильмы",
        description = "API администратора для работы с фильмами"
)
@ApiResponses(
        {
                @ApiResponse(
                        responseCode = "401",
                        description = "Ошибка авторизации",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = ErrorResponse.class)
                        )
                ),
                @ApiResponse(
                        responseCode = "500",
                        description = "Ошибка на сервере",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = ErrorResponse.class)
                        )
                )
        }
)
public class MovieAdminController {

    private final AdminValidator adminValidator;
    private final MovieService movieService;

    @Operation(
            summary = "Создает фильм",
            description = "Сохраняет фильм в базу данных "
    )
    @ApiResponse(
            responseCode = "201",
            description = "Фильм создан",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = MovieAdminDto.class)
            )
    )
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public MovieAdminDto createMovie(@RequestHeader("user-token") String token,
                                     @RequestBody MovieCreateAdminDto dto) {
        adminValidator.valid(token);
        MovieAdminDto movie = movieService.create(dto);
        return movie;
    }
}
