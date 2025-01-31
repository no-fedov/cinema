package javaacademy.com.cinema.repository;

import javaacademy.com.cinema.entity.Place;

import java.util.List;
import java.util.Optional;

public interface PlaceRepository {
    Optional<Place> findById(Integer id);

    List<Place> findAll();
}
