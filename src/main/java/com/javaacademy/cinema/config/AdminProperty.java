package com.javaacademy.cinema.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.admin")
@Getter
@Setter
@AllArgsConstructor
public class AdminProperty {
    private String token;
}
