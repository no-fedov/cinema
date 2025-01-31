package javaacademy.com.cinema.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties(AdminProperty.class)
public class AdminAuthenticationConfig {
}
