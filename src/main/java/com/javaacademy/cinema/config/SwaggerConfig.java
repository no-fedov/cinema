package com.javaacademy.cinema.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("Это api приложения \"Кинотеатр\"")
                .description("Приложение позволяет администратору регистрировать фильмы в БД, " +
                        "создавать для них сеансы и билеты на сеансы."
                        + " Пользователь может купить билет на сеанс, просмотреть список фильмов и сеансов");
        return new OpenAPI().info(info);
    }
}
