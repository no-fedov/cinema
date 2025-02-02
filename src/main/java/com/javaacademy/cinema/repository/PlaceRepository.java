package com.javaacademy.cinema.repository;

import com.javaacademy.cinema.entity.Place;

import java.util.List;
import java.util.Optional;

public interface PlaceRepository {
    Optional<Place> findById(Integer id);

    List<Place> findAll();
}
