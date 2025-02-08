package com.javaacademy.cinema.web;

import com.javaacademy.cinema.dto.SessionCreateAdminDto;
import com.javaacademy.cinema.entity.Movie;
import com.javaacademy.cinema.repository.MovieRepository;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
    private int sessionIdAfterSaveEntity;
    private int movieIdAfterSaveEntity;
    private final RequestSpecification requestSpecification = new RequestSpecBuilder()
            .setPort(9999)
            .setBasePath("/session")
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    @Autowired
    private static JdbcTemplate jdbcTemplate;

    @Autowired
    private MovieRepository movieRepository;

    @BeforeAll
    public static void initSequence() {
        jdbcTemplate.execute(INIT_MOVIE_SEQUENCE);
        jdbcTemplate.execute(INIT_SESSION_SEQUENCE);
    }

    @BeforeEach
    public void setMaxMovieId() {
        sessionIdAfterSaveEntity = jdbcTemplate.queryForObject(CURRENT_VALUE_SESSION_SEQUENCE_QUERY, Integer.class) + 1;
        movieIdAfterSaveEntity = jdbcTemplate.queryForObject(CURRENT_VALUE_MOVIE_SEQUENCE_QUERY, Integer.class) + 1;
    }

    @Test
    @DisplayName("Успешное создание сеанса и билетов на этот сеанс")
    public void createSessionSuccess() {
        Movie savedMovie = movieRepository.save(new Movie(null, "name", "description"));
        Integer currentMovieId = savedMovie.getId();
        assertEquals(movieIdAfterSaveEntity, currentMovieId);
        SessionCreateAdminDto dto = new SessionCreateAdminDto(
                currentMovieId,
                LocalDateTime.of(2020, 1, 1, 1, 1),
                new BigDecimal("1000")
        );
        RestAssured.given(requestSpecification)
                .body(dto)
                .header("user-token", "secretadmib123")
                .post()
                .then()
                .statusCode(HttpStatus.OK)
    }
}
