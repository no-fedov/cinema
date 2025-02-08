package com.javaacademy.cinema.controller.admin;

import com.javaacademy.cinema.config.AdminProperty;
import com.javaacademy.cinema.exception.AuthenticationException;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class AdminValidator {

    private final AdminProperty adminProperty;

    public void valid(String token) {
        if (!Objects.equals(token, adminProperty.getToken())) {
            throw new AuthenticationException("Нет прав доступа, авторизуйтесь как администратор");
        }
    }
}
