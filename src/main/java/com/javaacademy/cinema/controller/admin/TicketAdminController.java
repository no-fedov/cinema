package com.javaacademy.cinema.controller.admin;

import com.javaacademy.cinema.controller.ErrorResponse;
import com.javaacademy.cinema.dto.SessionAdminDto;
import com.javaacademy.cinema.dto.TicketAdminDto;
import com.javaacademy.cinema.service.admin.TicketAdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.lang.Boolean.TRUE;

@Slf4j
@RestController
@RequestMapping("/ticket")
@RequiredArgsConstructor
@Tag(
        name = "Admin: Билеты",
        description = "API администратора для работы с билетами"
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
public class TicketAdminController {

    private final AdminValidator adminValidator;
    private final TicketAdminService ticketAdminService;

    @Operation(
            summary = "Возвращает все купленные билеты",
            description = "Позволяет посмотреть проданные билеты, хранящиеся в базе данных"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Билеты найдены",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(
                            schema = @Schema(implementation = TicketAdminDto.class)
                    )
            )
    )
    @GetMapping("/saled")
    public List<TicketAdminDto> soldTickets(@RequestHeader("user-token") String token) {
        adminValidator.valid(token);
        List<TicketAdminDto> soldTickets = ticketAdminService.getBySoldCondition(TRUE);
        log.info("Найдены все проданные билеты: {}", soldTickets);
        return soldTickets;
    }

}
