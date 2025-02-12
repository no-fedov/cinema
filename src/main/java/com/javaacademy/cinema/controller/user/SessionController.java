package com.javaacademy.cinema.controller.user;

import com.javaacademy.cinema.controller.ErrorResponse;
import com.javaacademy.cinema.dto.PlaceDto;
import com.javaacademy.cinema.dto.SessionDto;
import com.javaacademy.cinema.service.user.PlaceService;
import com.javaacademy.cinema.service.user.SessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/session")
@RequiredArgsConstructor
@Tag(
        name = "Public: Сеансы",
        description = "Публичный API для работы с сеансами"
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
public class SessionController {

    private final SessionService sessionService;
    private final PlaceService placeService;

    @Operation(
            summary = "Находит сеансы",
            description = "Находит все существующие в БД сеансы"
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Найдены сеансы",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(
                                                    implementation = SessionDto.class
                                            )
                                    )
                            )
                    )
            }
    )
    @GetMapping
    public List<SessionDto> getAll() {
        List<SessionDto> sessions = sessionService.getAll();
        return sessions;
    }

    @Operation(
            summary = "Находит свободные места на указанный сеанс",
            description = "Возвращает перечень свободных мест"
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Найдены места",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = String.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Указанный сеанс не существует",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    )
            }
    )
    @GetMapping(path = "/{id}/free-place", produces = {"application/json", "text/plain"})
    public String freeSeatsBySessionId(@PathVariable("id") Integer sessionId) {
        List<PlaceDto> freePlace = placeService.getFreeBySessionId(sessionId);
        String placeAsString = freePlace.stream().map(PlaceDto::toString)
                .reduce((e1, e2) -> e1.concat(", ").concat(e2)).orElse("");
        return "[".concat(placeAsString).concat("]");
    }
}
