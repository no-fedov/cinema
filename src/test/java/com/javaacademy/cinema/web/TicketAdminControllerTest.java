package com.javaacademy.cinema.web;

import com.javaacademy.cinema.config.AdminProperty;
import com.javaacademy.cinema.controller.ErrorResponse;
import com.javaacademy.cinema.dto.TicketAdminDto;
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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@AutoConfigureWebMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Sql(scripts = "classpath:clear-db.sql")
public class TicketAdminControllerTest {

    private static final LocalDateTime SESSION_TIME = LocalDateTime.of(
            2020,
            10,
            10,
            10,
            10
    );

    private final RequestSpecification requestSpecification = new RequestSpecBuilder()
            .setBasePath("/ticket")
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private AdminProperty adminProperty;

    @Test
    @DisplayName("Поиск всех проданных билетов")
    public void findSoldTicketsSuccess() {
        Movie movie = movieRepository.save(new Movie(null, "name", "description"));
        Session newSession = new Session(null, SESSION_TIME, new BigDecimal("1000"), movie);
        Session savedSession = sessionRepository.save(newSession);
        Ticket newTicket = new Ticket(null, savedSession, new Place(1, "A1"), false);
        Ticket savedTicket = ticketRepository.save(newTicket);
        Ticket soldTicket = ticketRepository.buy(savedTicket.getId());
        List<TicketAdminDto> soldTickets = RestAssured.given(requestSpecification)
                .header("user-token", adminProperty.getToken())
                .get("/saled")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .body()
                .as(new TypeRef<List<TicketAdminDto>>() {
                });
        assertEquals(1, soldTickets.size());
        boolean ticketIsSold = soldTickets.stream()
                .anyMatch(t -> t.getId().equals(soldTicket.getId()) && t.getIsSold());
        assertTrue(ticketIsSold);
    }

    @Test
    @DisplayName("Поиск всех проданных билетов без авторизации")
    public void findSoldTicketsUnsuccessful() {
        ErrorResponse expectedResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(),
                "Нет прав доступа, авторизуйтесь как администратор");
        ErrorResponse response = RestAssured.given(requestSpecification)
                .header("user-token", "randompassword")
                .get("/saled")
                .then()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .extract()
                .body()
                .as(ErrorResponse.class);
        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getCode());
        assertEquals(expectedResponse, response);
    }
}
