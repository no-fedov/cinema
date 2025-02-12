package com.javaacademy.cinema.controller.user;

import com.javaacademy.cinema.controller.ErrorResponse;
import com.javaacademy.cinema.dto.TicketBookingDto;
import com.javaacademy.cinema.dto.TicketDto;
import com.javaacademy.cinema.service.user.TicketService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ticket")
@Tag(
        name = "Public: Билеты",
        description = "Публичный API для работы с билетами"
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
public class TicketController {

    private final TicketService ticketService;

    @Operation(
            summary = "Покупка билета",
            description = "Позволяет пользователю купить билет по номеру сессии и названию места"
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Билет куплен",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = TicketDto.class
                                    )

                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Не найдено свободное место или сеанс",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ErrorResponse.class
                                    )

                            )
                    )
            }
    )
    @PostMapping("/booking")
    @ResponseStatus(HttpStatus.CREATED)
    public TicketDto buyTicket(@RequestBody TicketBookingDto dto) {
        TicketDto ticketDto = ticketService.buy(dto);
        return ticketDto;
    }
}
