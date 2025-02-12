package com.javaacademy.cinema.controller.admin;

import com.javaacademy.cinema.controller.ErrorResponse;
import com.javaacademy.cinema.dto.SessionAdminDto;
import com.javaacademy.cinema.dto.SessionCreateAdminDto;
import com.javaacademy.cinema.service.admin.SessionAdminService;
import com.javaacademy.cinema.service.admin.TicketAdminService;
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
@RequestMapping("/session")
@Tag(
        name = "Admin: Сеансы",
        description = "API администратора для работы с сеансами"
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
public class SessionAdminController {

    private final AdminValidator adminValidator;
    private final SessionAdminService sessionAdminService;
    private final TicketAdminService ticketAdminService;

    @Operation(
            summary = "Создает сеанс",
            description = "Сохраняет сеанс в базу данных "
    )
    @ApiResponse(
            responseCode = "201",
            description = "Успешное создание сеанса",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = SessionAdminDto.class)
            )
    )
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public SessionAdminDto createSession(@RequestHeader("user-token") String token,
                                         @RequestBody SessionCreateAdminDto dto) {
        adminValidator.valid(token);
        SessionAdminDto session = sessionAdminService.create(dto);
        ticketAdminService.createForSession(session);
        return session;
    }
}
