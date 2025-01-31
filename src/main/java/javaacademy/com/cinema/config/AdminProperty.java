package javaacademy.com.cinema.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@ConfigurationProperties(prefix = "app.admin")
@Getter
@Setter
@AllArgsConstructor
public class AdminProperty {
    private String token;
}
