package com.javaacademy.cinema.web;

import com.javaacademy.cinema.controller.ErrorResponse;
import com.javaacademy.cinema.dto.SessionDto;
import com.javaacademy.cinema.entity.Movie;
import com.javaacademy.cinema.entity.Place;
import com.javaacademy.cinema.entity.Session;
import com.javaacademy.cinema.entity.Ticket;
import com.javaacademy.cinema.repository.MovieRepository;
import com.javaacademy.cinema.repository.SessionRepository;
import com.javaacademy.cinema.repository.TicketRepository;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.common.mapper.TypeRef;
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
public class SessionControllerTest {

    private RequestSpecification requestSpecification;

    @Value("${server.port}")
    int port;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private TicketRepository ticketRepository;

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
    @DisplayName("Поиск всех сеансов")
    public void getAllSessionSuccess() {
        Movie savedMovie = movieRepository.save(new Movie(null, "name", "description"));
        sessionRepository.save(new Session(null, LocalDateTime.now(), new BigDecimal("100"), savedMovie));
        List<SessionDto> sessionDtos = RestAssured.given(requestSpecification)
                .get()
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .body()
                .as(new TypeRef<List<SessionDto>>() {
                });
        assertEquals(1, sessionDtos.size());
    }

    @Test
    @DisplayName("Найти свободные места для сеанса")
    public void getFreeSeatsForSession() {
        Movie movie = movieRepository.save(new Movie(null, "name", "description"));
        Session session = sessionRepository.save(
                new Session(null, LocalDateTime.now(), new BigDecimal("100"), movie)
        );
        ticketRepository.save(new Ticket(null, session, new Place(1, "A1"), Boolean.FALSE));
        String expectedResponse = "['1A']";
        String response = RestAssured.given(requestSpecification)
                .get("/" + session.getId() + "/free-place")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .asString();
        assertEquals(expectedResponse, response);
    }

    @Test
    @DisplayName("Поиск мсвободных мест для несуществующего сеанса")
    public void getFreeSeatsUnsuccessful() {
        ErrorResponse expectedResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Сеанс с id = 1 не найден"
        );
        ErrorResponse response = RestAssured.given(requestSpecification)
                .get("/" + 1 + "/free-place")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .extract()
                .body()
                .as(ErrorResponse.class);
        assertEquals(expectedResponse, response);
    }
}
