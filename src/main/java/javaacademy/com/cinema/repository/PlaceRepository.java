package javaacademy.com.cinema.repository;

import javaacademy.com.cinema.entity.Place;
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
public class PlaceRepository {
    private final JdbcTemplate jdbcTemplate;

    public Optional<Place> findById(Integer id) {
        String sql = "select * from place where id = ?";
        Optional<Place> currentSession = jdbcTemplate.query(sql, this::mapToPlace, id).stream().findFirst();
        log.info("Обработан запрос {}, где id = {}. Найдено: {}", sql, id, currentSession);
        return currentSession;
    }

    public List<Place> findAll() {
        String sql = "select * from place";
        List<Place> places = jdbcTemplate.query(sql, this::mapToPlace);
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
