package javaacademy.com.cinema.repository;

import javaacademy.com.cinema.entity.Session;

import java.util.List;
import java.util.Optional;

public interface SessionRepository {
    Optional<Session> findById(Integer id);

    Session save(Session newSession);

    List<Session> findAll();
}
