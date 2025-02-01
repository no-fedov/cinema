package javaacademy.com.cinema.repository.imp;

import javaacademy.com.cinema.entity.Place;
import javaacademy.com.cinema.repository.PlaceRepository;
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

    private final JdbcTemplate jdbcTemplate;

    public Optional<Place> findById(Integer id) {
        Optional<Place> currentSession = jdbcTemplate.query(
                PLACE_BY_ID_QUERY,
                this::mapToPlace,
                id
        ).stream().findFirst();
        log.info("Обработан запрос {}, где id = {}. Найдено: {}", PLACE_BY_ID_QUERY, id, currentSession);
        return currentSession;
    }

    public List<Place> findAll() {
        List<Place> places = jdbcTemplate.query(
                ALL_PLACES_QUERY,
                this::mapToPlace
        );
        log.info("Найдены места: {}", places);
        return places;
    }

    @SneakyThrows
    private Place mapToPlace(ResultSet rs, int rowNum) {
        Place place = new Place();
        place.setId(rs.getInt("id"));
        place.setName(rs.getString("name"));
        return place;
    }
}
