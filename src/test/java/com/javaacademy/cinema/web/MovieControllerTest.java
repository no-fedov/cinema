package com.javaacademy.cinema.web;

import com.javaacademy.cinema.dto.MovieDto;
import com.javaacademy.cinema.entity.Movie;
import com.javaacademy.cinema.repository.MovieRepository;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureWebMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Sql(scripts = "classpath:clear-db.sql")
public class MovieControllerTest {

    private RequestSpecification requestSpecification;

    @Value("${server.port}")
    int port;

    @Autowired
    private MovieRepository movieRepository;

    @PostConstruct
    public void initRestAssuredSpec() {
        requestSpecification = new RequestSpecBuilder()
                .setPort(port)
                .setBasePath("/movie")
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();
    }

    @Test
    @DisplayName("Поиск всех фильмов")
    public void getAllMovieSuccess() {
        movieRepository.save(new Movie(null, "name", "description"));
        movieRepository.save(new Movie(null, "name", "description"));
        List<MovieDto> movieDtos = RestAssured.given(requestSpecification)
                .get()
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .body().as(new TypeRef<List<MovieDto>>() {
                });
        assertEquals(2, movieDtos.size());
    }
}
