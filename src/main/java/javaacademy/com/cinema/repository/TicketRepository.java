package javaacademy.com.cinema.repository;

import javaacademy.com.cinema.entity.Movie;
import javaacademy.com.cinema.entity.Place;
import javaacademy.com.cinema.entity.Session;
import javaacademy.com.cinema.entity.Ticket;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;
import java.util.Objects;
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

    public Ticket save(final Ticket newTicket) {
        String sql = """ 
                    insert into ticket (place_id, session_id, is_sold) values
                    (?, ?, false)
                    returning id;
                """;
        Integer placeId = newTicket.getPlace().getId();
        Integer sessionId = newTicket.getSession().getId();
        Integer ticketId = jdbcTemplate.queryForObject(sql, Integer.class, placeId, sessionId);
        newTicket.setId(ticketId);
        log.info("Сохранен новый билет: {}", newTicket);
        return newTicket;
    }

    public void sellTicket(Integer id) {
        findById(id).orElseThrow(() -> new RuntimeException("билет с id = %s не существует".formatted(id)));
        String sql = """
                    update ticket set is_sold = true
                    where ticket_id = ?;
                """;
        jdbcTemplate.update(sql, id);
    }

    public List<Ticket> findBySessionId(Integer sessionId, boolean isSold) {
        String sql = """
                    select t.*,
                        p.name as place_name,
                        s.movie_id,
                        s.price,
                        s.datetime,
                        m.name as movie_name,
                        m.description as movie_description
                    from ticket t
                    join place p on t.place_id = p.id
                    join session s on t.session_id = s.id
                    left join movie m on s.movie_id = m.id
                    where s.id = ? and t.is_sold = ?;
                """;
        List<Ticket> tickets = jdbcTemplate.query(sql, this::mapToTicketForList, sessionId, isSold);
        log.info("Найдены билеты на сеанс: {}", tickets);
        return tickets;
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

    @SneakyThrows
    private Ticket mapToTicketForList(ResultSet rs, int rowNum) {
        Ticket ticket = new Ticket();
        ticket.setId(rs.getInt("id"));
        ticket.setIsSold(rs.getBoolean("is_sold"));

        Place place = new Place();
        place.setId(rs.getInt("place_id"));
        place.setName(rs.getString("place_name"));
        ticket.setPlace(place);

        Session session = new Session();
        session.setId(rs.getInt("id"));
        session.setPrice(rs.getBigDecimal("price"));
        session.setDateTime(rs.getTimestamp("datetime").toLocalDateTime());

        String movieId = rs.getString("movie_id");
        if (Objects.nonNull(movieId)) {
            Movie movie = new Movie();
            movie.setId(Integer.parseInt(movieId));
            movie.setName(rs.getString("movie_name"));
            movie.setDescription(rs.getString("movie_description"));
            session.setMovie(movie);
        }
        ticket.setSession(session);
        return ticket;
    }
}
