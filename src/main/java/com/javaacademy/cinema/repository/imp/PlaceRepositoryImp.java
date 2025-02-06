package com.javaacademy.cinema.repository.imp;

import com.javaacademy.cinema.entity.Place;
import com.javaacademy.cinema.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class PlaceRepositoryImp implements PlaceRepository {

    private static final String PLACE_BY_ID_QUERY = "select * from place where id = ?";
    private static final String ALL_PLACES_QUERY = "select * from place";
    private static final String FREE_PLACES_BY_SESSION_QUERY = """
            select p.id, p.name
            from place p
            where p.id in (
                           select t.place_id
                           from ticket t
                           where session_id = ?
                           and is_sold = false
                           )
            order by p.id;
            """;
    private static final String PLACE_BY_NAME_QUERY = """
            select p.id, p.name
            from place p
            where p.name = ?;
            """;

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Place> findById(Integer id) {
        Optional<Place> place = jdbcTemplate.query(
                PLACE_BY_ID_QUERY,
                this::mapToPlace,
                id
        ).stream().findFirst();
        log.info("Обработан запрос {}, где id = {}. Найдено: {}", PLACE_BY_ID_QUERY, id, place);
        return place;
    }

    @Override
    public List<Place> findAll() {
        List<Place> places = jdbcTemplate.query(
                ALL_PLACES_QUERY,
                this::mapToPlace
        );
        log.info("Найдены места: {}", places);
        return places;
    }

    @Override
    public List<Place> findFreeBySessionId(Integer sessionId) {
        return jdbcTemplate.query(
                FREE_PLACES_BY_SESSION_QUERY,
                this::mapToPlace,
                sessionId
        );
    }

    @Override
    public Optional<Place> findByName(String placeName) {
        Optional<Place> place = jdbcTemplate.query(
                PLACE_BY_NAME_QUERY,
                this::mapToPlace,
                placeName
        ).stream().findFirst();
        return place;
    }

    @SneakyThrows
    private Place mapToPlace(ResultSet rs, int rowNum) {
        Place place = new Place();
        place.setId(rs.getInt("id"));
        place.setName(rs.getString("name"));
        return place;
    }
}
