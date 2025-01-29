package javaacademy.com.cinema.repository;

import javaacademy.com.cinema.entity.Ticket;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class TicketRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SessionRepository sessionRepository;
    private final PlaceRepository placeRepository;

    public Optional<Ticket> findById(Integer id) {
        String sql = "select * from ticket where id = ?";
        Optional<Ticket> currentTicket = jdbcTemplate.query(sql, this::mapToTicket, id).stream().findFirst();
        log.info("Обработан запрос {}, где id = {}. Найдено: {}", sql, id, currentTicket);
        return currentTicket;
    }

    @SneakyThrows
    private Ticket mapToTicket(ResultSet rs, int rowNum) {
        Ticket ticket = new Ticket();
        ticket.setId(rs.getInt("id"));
        ticket.setIsSold(rs.getBoolean("is_sold"));
        ticket.setPlace(placeRepository.findById(rs.getInt("place_id")).get());
        ticket.setSession(sessionRepository.findById(rs.getInt("session_id")).get());
        return ticket;
    }
}
