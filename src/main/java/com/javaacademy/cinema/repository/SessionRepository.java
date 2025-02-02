package com.javaacademy.cinema.repository;

import com.javaacademy.cinema.entity.Session;

import java.util.List;
import java.util.Optional;

public interface SessionRepository {
    Optional<Session> findById(Integer id);

    Session save(Session newSession);

    List<Session> findAll();
}
