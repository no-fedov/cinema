package com.javaacademy.cinema.web;

import com.javaacademy.cinema.config.AdminProperty;
import com.javaacademy.cinema.controller.ErrorResponse;
import com.javaacademy.cinema.dto.MovieAdminDto;
import com.javaacademy.cinema.dto.SessionAdminDto;
import com.javaacademy.cinema.dto.SessionCreateAdminDto;
import com.javaacademy.cinema.entity.Movie;
import com.javaacademy.cinema.entity.Session;
import com.javaacademy.cinema.entity.Ticket;
import com.javaacademy.cinema.repository.MovieRepository;
import com.javaacademy.cinema.repository.SessionRepository;
import com.javaacademy.cinema.repository.TicketRepository;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureWebMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Sql(scripts = "classpath:clear-db.sql")
public class SessionAdminControllerTest {

    private static final int TICKET_COUNT_FOR_SESSION = 10;
    private static final LocalDateTime SESSION_TIME = LocalDateTime.of(
            2020,
            10,
            10,
            10,
            10
    );
    @Value("${server.port}")
    int port;

    private RequestSpecification requestSpecification;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private AdminProperty adminProperty;

    @PostConstruct
    public void initRestAssuredSpec() {
        requestSpecification = new RequestSpecBuilder()
                .setPort(port)
                .setBasePath("/session")
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();
    }

    @Test
    @DisplayName("Успешное создание сеанса и билетов на этот сеанс")
    public void createSessionSuccess() {
        Movie savedMovie = movieRepository.save(new Movie(null, "name", "description"));
        SessionAdminDto expectedSessionAdminDto = new SessionAdminDto(
                null,
                new BigDecimal("1000"),
                SESSION_TIME,
                new MovieAdminDto(null, "name", "description")
        );
        Session expectedSession = new Session(
                null,
                SESSION_TIME,
                new BigDecimal("1000"),
                savedMovie
        );
        SessionCreateAdminDto dto = new SessionCreateAdminDto(
                savedMovie.getId(),
                SESSION_TIME,
                new BigDecimal("1000")
        );
        SessionAdminDto savedSessionAdminDto = RestAssured.given(requestSpecification)
                .body(dto)
                .header("user-token", adminProperty.getToken())
                .post()
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .body()
                .as(SessionAdminDto.class);
        assertEquals(expectedSessionAdminDto.getDateTime(), savedSessionAdminDto.getDateTime());
        assertEquals(expectedSessionAdminDto.getPrice(), savedSessionAdminDto.getPrice());
        assertEquals(expectedSessionAdminDto.getMovie().getName(), savedSessionAdminDto.getMovie().getName());
        assertEquals(expectedSessionAdminDto.getMovie().getDescription(),
                savedSessionAdminDto.getMovie().getDescription()
        );
        Session savedSession = sessionRepository.findById(savedSessionAdminDto.getId()).get();
        assertEquals(expectedSession.getDateTime(), savedSession.getDateTime());
        assertEquals(expectedSession.getPrice(), savedSession.getPrice());
        assertEquals(expectedSession.getMovie().getDescription(), savedSession.getMovie().getDescription());
        assertEquals(expectedSession.getMovie().getName(), savedSession.getMovie().getName());
        List<Ticket> tickets = ticketRepository.findBySessionId(savedSessionAdminDto.getId(), false);
        assertEquals(TICKET_COUNT_FOR_SESSION, tickets.stream().filter(t -> !t.getIsSold()).count());
    }

    @Test
    @DisplayName("Ошибка авторизации при попытке создания фильма")
    public void createSessionUnsuccessful() {
        ErrorResponse expectedResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(),
                "Нет прав доступа, авторизуйтесь как администратор");
        SessionCreateAdminDto sessionDto = new SessionCreateAdminDto(
                1,
                SESSION_TIME,
                new BigDecimal("100")
        );
        ErrorResponse response = RestAssured.given(requestSpecification)
                .body(sessionDto)
                .header("user-token", "badpassword")
                .post()
                .then()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .extract()
                .body()
                .as(ErrorResponse.class);
        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getCode());
        assertEquals(expectedResponse, response);
    }
}
