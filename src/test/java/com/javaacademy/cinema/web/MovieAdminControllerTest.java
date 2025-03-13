package com.javaacademy.cinema.web;

import com.javaacademy.cinema.config.AdminProperty;
import com.javaacademy.cinema.controller.ErrorResponse;
import com.javaacademy.cinema.dto.MovieAdminDto;
import com.javaacademy.cinema.dto.MovieCreateAdminDto;
import com.javaacademy.cinema.entity.Movie;
import com.javaacademy.cinema.repository.MovieRepository;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@AutoConfigureWebMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Sql(scripts = "classpath:clear-db.sql")
public class MovieAdminControllerTest {

    private final RequestSpecification requestSpecification = new RequestSpecBuilder()
                .setBasePath("/movie")
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private AdminProperty adminProperty;

    @Test
    @DisplayName("Успешное создание фильма")
    public void createMovieSuccess() {
        MovieCreateAdminDto dto = new MovieCreateAdminDto("film_name", "film_description");
        MovieAdminDto expectedMovieAdminDto = new MovieAdminDto(
                null,
                "film_name",
                "film_description"
        );
        MovieAdminDto responseMovieAdminDto = RestAssured.given(requestSpecification)
                .header("user-token", adminProperty.getToken())
                .body(dto)
                .post()
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .body()
                .as(MovieAdminDto.class);
        assertEquals(expectedMovieAdminDto.getDescription(), responseMovieAdminDto.getDescription());
        assertEquals(expectedMovieAdminDto.getName(), responseMovieAdminDto.getName());
        assertNotNull(responseMovieAdminDto.getId());
        Movie savedMovie = movieRepository.findById(responseMovieAdminDto.getId()).orElseThrow();
        assertEquals(dto.getName(), savedMovie.getName());
        assertEquals(dto.getDescription(), savedMovie.getDescription());
    }

    @Test
    @DisplayName("Ошибка доступа при создании фильма")
    public void createMovieWithoutAccess() {
        ErrorResponse expectedResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(),
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
        assertEquals(expectedResponse, response);
    }
}
