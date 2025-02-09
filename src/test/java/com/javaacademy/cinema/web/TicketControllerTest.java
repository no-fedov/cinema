package com.javaacademy.cinema.web;

import com.javaacademy.cinema.controller.ErrorResponse;
import com.javaacademy.cinema.dto.TicketBookingDto;
import com.javaacademy.cinema.dto.TicketDto;
import com.javaacademy.cinema.entity.Movie;
import com.javaacademy.cinema.entity.Place;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureWebMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Sql(scripts = "classpath:clear-db.sql")
public class TicketControllerTest {

    private static final LocalDateTime SESSION_TIME = LocalDateTime.of(
            2000,
            10,
            10,
            10,
            10);

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
                .setBasePath("/ticket/booking")
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();
    }

    @Test
    @DisplayName("Упешная покупка билета")
    public void buyTicketSuccess() {
        Movie movie = movieRepository.save(new Movie(null, "name", "description"));
        Session session = sessionRepository.save(new Session(null, SESSION_TIME, new BigDecimal("100"), movie));
        Ticket ticket = ticketRepository.save(
                new Ticket(null, session, new Place(1, "A1"), Boolean.FALSE)
        );
        TicketBookingDto dto = new TicketBookingDto(session.getId(), "A1");
        String expectedPlaceName = "'1A'";
        TicketDto soldTicket = RestAssured.given(requestSpecification)
                .body(dto)
                .post()
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .body()
                .as(TicketDto.class);
        assertEquals(ticket.getId(), soldTicket.getId());
        assertEquals(ticket.getSession().getMovie().getName(), soldTicket.getMovieName());
        assertEquals(ticket.getSession().getDateTime(), soldTicket.getDate());
        assertEquals(expectedPlaceName, soldTicket.getPlaceName());
    }

    @Test
    @DisplayName("Покупка билета на несуществующий сеанс")
    public void buyTicketUnsuccessful() {
        TicketBookingDto dto = new TicketBookingDto(1, "A1");
        ErrorResponse expectedResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Билет на сеанс с id = 1 и место = A1 не найден"
        );
        ErrorResponse response = RestAssured.given(requestSpecification)
                .body(dto)
                .post()
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .extract()
                .body()
                .as(ErrorResponse.class);
        assertEquals(expectedResponse, response);
    }
}
