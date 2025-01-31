package javaacademy.com.cinema.repository;

import javaacademy.com.cinema.entity.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieRepository {
    Optional<Movie> findById(Integer id);

    Movie save(Movie newMovie);

    List<Movie> findAll();
}
