package com.javaacademy.cinema.web;

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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureWebMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SessionAdminControllerTest {
    private static final String INIT_MOVIE_SEQUENCE = """
            select nextval('movie_id_seq');
            """;
    private static final String INIT_SESSION_SEQUENCE = """
            select nextval('session_id_seq');
            """;
    private static final String CURRENT_VALUE_SESSION_SEQUENCE_QUERY = """
            select currval('session_id_seq');
            """;
    private static final String CURRENT_VALUE_MOVIE_SEQUENCE_QUERY = """
            select currval('movie_id_seq');
            """;
    private static final int TICKET_COUNT_FOR_SESSION = 10;
    private static final LocalDateTime sessionTime = LocalDateTime.of(
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
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @BeforeEach
    public void setMaxMovieId() {
        jdbcTemplate.execute(INIT_MOVIE_SEQUENCE);
        jdbcTemplate.execute(INIT_SESSION_SEQUENCE);
    }

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
        Integer currentMovieId = savedMovie.getId();
        Integer currentMovieIdDB = jdbcTemplate.queryForObject(CURRENT_VALUE_MOVIE_SEQUENCE_QUERY, Integer.class);
        assertEquals(currentMovieIdDB, currentMovieId);
        int maxSessionId = jdbcTemplate.queryForObject(CURRENT_VALUE_SESSION_SEQUENCE_QUERY, Integer.class);
        SessionAdminDto expectedSessionAdminDto = new SessionAdminDto(
                maxSessionId + 1,
                new BigDecimal("1000"),
                sessionTime,
                new MovieAdminDto(currentMovieId, "name", "description")
        );
        Session expectedSession = new Session(
                maxSessionId + 1,
                sessionTime,
                new BigDecimal("1000"),
                savedMovie
        );
        SessionCreateAdminDto dto = new SessionCreateAdminDto(
                currentMovieId,
                sessionTime,
                new BigDecimal("1000")
        );
        SessionAdminDto savedSessionAdminDto = RestAssured.given(requestSpecification)
                .body(dto)
                .header("user-token", "secretadmin123")
                .post()
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .body()
                .as(SessionAdminDto.class);
        assertEquals(expectedSessionAdminDto, savedSessionAdminDto);
        Session savedSession = sessionRepository.findById(maxSessionId + 1).get();
        assertEquals(expectedSession, savedSession);
        int currentSessionId = savedSession.getId();
        List<Ticket> tickets = ticketRepository.findBySessionId(currentSessionId, false);
        assertEquals(TICKET_COUNT_FOR_SESSION, tickets.stream().filter(t -> !t.getIsSold()).count());
    }
}
