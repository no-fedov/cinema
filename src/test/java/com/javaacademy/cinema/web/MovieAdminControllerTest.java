package com.javaacademy.cinema.web;

import com.javaacademy.cinema.dto.MovieAdminDto;
import com.javaacademy.cinema.dto.MovieCreateAdminDto;
import com.javaacademy.cinema.exception.ErrorResponse;
import com.javaacademy.cinema.repository.MovieRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureWebMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class MovieAdminControllerTest {
    private static final String CURRENT_VALUE_MOVIE_SEQUENCE_QUERY = """
            select currval('movie_id_seq');
            """;
    private static final String INIT_MOVIE_SEQUENCE = """
            select nextval('movie_id_seq');
            """;
    private RequestSpecification requestSpecification;

    @Value("${server.port}")
    int port;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void initRestAssuredSpec() {
        requestSpecification = new RequestSpecBuilder()
                .setPort(port)
                .setBasePath("/movie")
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();
    }

    @BeforeEach
    public void initSequence() {
        jdbcTemplate.execute(INIT_MOVIE_SEQUENCE);
    }

    @Test
    @DisplayName("Успешное создание фильма")
    public void createMovieSuccess() {
        int currentMovieSeqValue = jdbcTemplate.queryForObject(CURRENT_VALUE_MOVIE_SEQUENCE_QUERY, Integer.class);
        MovieCreateAdminDto dto = new MovieCreateAdminDto("film_name", "film_description");
        MovieAdminDto expectedMovieAdminDto = new MovieAdminDto(
                currentMovieSeqValue + 1,
                "film_name",
                "film_description"
        );
        MovieAdminDto responseMovieAdminDto = RestAssured.given(requestSpecification)
                .header("user-token", "secretadmin123")
                .body(dto)
                .post()
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .body()
                .as(MovieAdminDto.class);
        assertEquals(expectedMovieAdminDto, responseMovieAdminDto);
    }

    @Test
    @DisplayName("Ошибка доступа при создании фильма")
    public void createMovieWithoutAccess() {
        ErrorResponse expectedResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Нет прав доступа, авторизуйтесь как администратор");
        MovieCreateAdminDto dto = new MovieCreateAdminDto("film_name", "film_description");
        ErrorResponse response = RestAssured.given(requestSpecification)
                .header("user-token", "random_password")
                .body(dto)
                .post()
                .then()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .extract()
                .body()
                .as(ErrorResponse.class);
        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getCode());
    }
}
